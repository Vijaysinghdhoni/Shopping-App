package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.databinding.BestDealsRvItemLayoutBinding

class BestDealsAdapter :
    PagingDataAdapter<Product, BestDealsAdapter.BestDealsViewholder>(CallBack) {

    object CallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewholder {
        val bestDealsRvItemLayoutBinding =
            BestDealsRvItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BestDealsViewholder((bestDealsRvItemLayoutBinding))
    }


    override fun onBindViewHolder(holder: BestDealsViewholder, position: Int) {

        val product = getItem(position)

        product?.let {
            holder.bind(it)

            holder.itemView.setOnClickListener {_->
                onclick?.invoke(it)
            }
        }


    }

    var onclick: ((Product) -> Unit)? = null

    inner class BestDealsViewholder(private val bestDealsRvItemLayoutBinding: BestDealsRvItemLayoutBinding) :
        RecyclerView.ViewHolder(bestDealsRvItemLayoutBinding.root) {

        fun bind(product: Product) {

            bestDealsRvItemLayoutBinding.productName.text = product.productName
            bestDealsRvItemLayoutBinding.productOrginalPrice.text = "Rs${product.price.toString()}"

            bestDealsRvItemLayoutBinding.productOfferPrice.apply {
                val discountAmount = product.price * (product.offerPercentage?.div(100)!!)
                val offerPrice = product.price - discountAmount
                text = "Rs${offerPrice.toString()}"
                bestDealsRvItemLayoutBinding.productOrginalPrice.paintFlags =
                    Paint.STRIKE_THRU_TEXT_FLAG
            }


            Glide.with(bestDealsRvItemLayoutBinding.productImg)
                .load(product.images[0])
                .placeholder(R.drawable.shopping_placeholder)
                .into(bestDealsRvItemLayoutBinding.productImg)


        }

    }
}
