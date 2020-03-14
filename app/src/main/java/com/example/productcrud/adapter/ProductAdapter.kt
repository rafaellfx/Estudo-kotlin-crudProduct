package com.example.productcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productcrud.R
import com.example.productcrud.models.Product

class ProductAdapter(private var listaProdutos: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>(){

    var onItemClickListener: OnItemClickListener? = null

    class MyViewHolder : RecyclerView.ViewHolder{

        var textName: TextView
        var textValor: TextView

        constructor(view: View) : super(view){

            textName = view.findViewById(R.id.name)
            textValor = view.findViewById(R.id.price)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
       val view = LayoutInflater.from(parent.getContext())
           .inflate(R.layout.item_product, parent, false) as View
        return MyViewHolder(view)
    }

    override fun getItemCount() = listaProdutos.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textName.text = listaProdutos.get(position).nome
        holder.textValor.text = "R$ " + listaProdutos.get(position).valor.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(
                holder.itemView,
                position
            )
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(view: View, position: Int)
    }
}