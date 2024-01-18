package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.databinding.BestProductItemsBinding
import com.vijaydhoni.shoppingapp.databinding.ProductdetailmageviewitemBinding

class ProductDetailViewPagerAdapter :
    RecyclerView.Adapter<ProductDetailViewPagerAdapter.ProductDetailViewPagerViewholder>() {


    private val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailViewPagerViewholder {
        val binding =
            ProductdetailmageviewitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProductDetailViewPagerViewholder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductDetailViewPagerViewholder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }


    inner class ProductDetailViewPagerViewholder(private val binding: ProductdetailmageviewitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {

            Glide.with(binding.imageView)
                .load(image)
                .placeholder(R.drawable.shopping_placeholder)
                .into(binding.imageView)

        }
    }


}