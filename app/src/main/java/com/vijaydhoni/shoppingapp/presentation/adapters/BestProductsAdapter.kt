package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.databinding.BestProductItemsBinding

class BestProductsAdapter :
    PagingDataAdapter<Product, BestProductsAdapter.BestProductViewholder>(callback) {

    object callback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductViewholder {
        val binding =
            BestProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BestProductViewholder(binding)
    }


    override fun onBindViewHolder(holder: BestProductViewholder, position: Int) {
        val product = getItem(position)
        product?.let { products ->
            holder.bind(products)

            holder.itemView.setOnClickListener {
                onclick?.invoke(products)
            }
        }

    }

    var onclick: ((Product) -> Unit)? = null


    inner class BestProductViewholder(private val binding: BestProductItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            binding.productName.text = product.productDescription
            binding.productOrginalPrice.text = "Rs${product.price.toString()}"
            binding.productOrginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


            product.offerPercentage?.let {
                binding.productOfferPrice.apply {
                    val discountAmount = product.price * (it.div(100))
                    val offerPrice = product.price - discountAmount
                    text = "Rs${offerPrice.toString()}"
                }

            }


            Glide.with(binding.productImg)
                .load(product.images[0])
                .placeholder(R.drawable.shopping_placeholder)
                .into(binding.productImg)

        }
    }

}