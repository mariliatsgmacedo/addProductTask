package com.macedos.myapplication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.macedos.myapplication.databinding.ActivityMainBinding
import com.macedos.myapplication.model.ProductName

class MainActivity : AppCompatActivity(), ListProductAdapter.ProductListListener {

    private lateinit var adapter: ListProductAdapter
    private lateinit var binding: ActivityMainBinding
    private var productList: ArrayList<ProductName> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        adapter = ListProductAdapter(productList,this)
        binding.recyclerListGeneral.adapter = adapter

        binding.addActionButton.setOnClickListener {
            openAddProductDialog()
        }
    }

    private fun addProduct(name: String){
        productList.add(ProductName(name))
        adapter.notifyDataSetChanged()
        toggleEmptyMessage()
        Toast.makeText(this,getString(R.string.success),Toast.LENGTH_SHORT).show()
    }

    private fun deleteProduct(item: ProductName){
        productList.remove(item)
        adapter.notifyDataSetChanged()
        toggleEmptyMessage()
    }

    private fun toggleEmptyMessage(){
        binding.linearLayoutMain.visibility = if (productList.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerListGeneral.visibility = if (productList.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun openAddProductDialog(){
        val builder = AlertDialog.Builder(this)
        val layout = LayoutInflater.from(this).inflate(R.layout.alert_add_product,null)
        builder.setView(layout)

        val inputUser = layout.findViewById<TextInputEditText>(R.id.input_text_user)
        val addButton = layout.findViewById<ExtendedFloatingActionButton>(R.id.btn_add_product)
        val alert = builder.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addButton.setOnClickListener {
            val name = inputUser.text.toString().trim()
            if (name.isNotEmpty()){
                addProduct(name)
                alert.dismiss()
            } else{
                inputUser.error = getString(R.string.required_field)
            }
        }
    }

    override fun delete(item: ProductName) {
        val builder = AlertDialog.Builder(this)
        val layout = LayoutInflater.from(this).inflate(R.layout.alert_message_del,null)
        builder.setView(layout)

        val alert = builder.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val yesBtn = layout.findViewById<ExtendedFloatingActionButton>(R.id.btn_yes)
        val noBtn = layout.findViewById<ExtendedFloatingActionButton>(R.id.btn_no)

        yesBtn.setOnClickListener {
            alert.dismiss()
            deleteProduct(item)
        }

        noBtn.setOnClickListener {
            alert.dismiss()
        }
    }
}