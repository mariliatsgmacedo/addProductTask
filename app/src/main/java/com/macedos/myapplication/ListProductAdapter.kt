package com.macedos.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.macedos.myapplication.model.ProductName

class ListProductAdapter(
    var productList: ArrayList<ProductName>,
    var listener: ProductListListener
) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_item)
        val myCardView : MaterialCardView = view.findViewById(R.id.model_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = productList[position].textProductName
        holder.myCardView.setOnLongClickListener {
            listener.delete(productList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface ProductListListener {
        fun delete(item: ProductName)
    }


}