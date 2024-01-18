package com.vijaydhoni.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.databinding.SpecialProductItemLayoutBinding

class SpecialProductAdapter :
    PagingDataAdapter<Product, SpecialProductAdapter.SpecialProductViewHolder>(CallBack) {

    object CallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductViewHolder {
        val specialProductItemLayoutBinding = SpecialProductItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecialProductViewHolder(specialProductItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: SpecialProductViewHolder, position: Int) {
        val product = getItem(position)

        product?.let {
            holder.bind(it)

            holder.itemView.setOnClickListener { _ ->
                onClick?.invoke(it)
            }
        }


    }


    var onClick: ((Product) -> Unit)? = null

    inner class SpecialProductViewHolder(private val specialProductItemLayoutBinding: SpecialProductItemLayoutBinding) :
        RecyclerView.ViewHolder(specialProductItemLayoutBinding.root) {

        fun bind(product: Product) {

            specialProductItemLayoutBinding.productName.text = product.productDescription
            specialProductItemLayoutBinding.productPrice.text = "Rs${product.price.toString()}"

            Glide.with(specialProductItemLayoutBinding.productImg)
                .load(product.images[0])
                .placeholder(R.drawable.shopping_placeholder)
                .into(specialProductItemLayoutBinding.productImg)
        }
    }
}

