package com.vijaydhoni.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.databinding.SearchRvItemBinding

class SearchRvAdapter : RecyclerView.Adapter<SearchRvAdapter.SearchRvViewHolder>() {

    val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.sizes == newItem.sizes
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRvViewHolder {
        return SearchRvViewHolder(
            SearchRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchRvViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }


    var onClick: ((Product) -> Unit)? = null

    inner class SearchRvViewHolder(private val binding: SearchRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchProduct: Product) {
            binding.productName.text = searchProduct.productName
            binding.openProductArrow.setOnClickListener {
                onClick?.invoke(searchProduct)
            }
        }


    }

}