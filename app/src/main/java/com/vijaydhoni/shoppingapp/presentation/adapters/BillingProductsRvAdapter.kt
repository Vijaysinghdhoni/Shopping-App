package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.databinding.BillingRvProductsItemBinding

class BillingProductsRvAdapter :
    RecyclerView.Adapter<BillingProductsRvAdapter.BillingProductRvViewholder>() {

    private val callback = object : DiffUtil.ItemCallback<UserCartProduct>() {
        override fun areItemsTheSame(oldItem: UserCartProduct, newItem: UserCartProduct): Boolean {
            return oldItem.quantity == newItem.quantity
        }

        override fun areContentsTheSame(
            oldItem: UserCartProduct,
            newItem: UserCartProduct
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductRvViewholder {
        return BillingProductRvViewholder(
            BillingRvProductsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BillingProductRvViewholder, position: Int) {

        val cartProduct = differ.currentList[position]
        holder.bind(cartProduct)

    }


    inner class BillingProductRvViewholder(private val binding: BillingRvProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(userCartProduct: UserCartProduct) {

            binding.productName.text = userCartProduct.product.productName
            binding.productPrice.text = userCartProduct.product.price.toString()
            binding.productQuantity.text = userCartProduct.quantity.toString()

            userCartProduct.product.offerPercentage?.let {
                binding.productPrice.apply {
                    val discountAmount = userCartProduct.product.price * (it.div(100))
                    val offerPrice = userCartProduct.product.price - discountAmount
                    text = "Rs${offerPrice.toString()}"
                }

            }

            userCartProduct.selectedColor?.let {
                val color = ColorDrawable(it)
                binding.productColor.setImageDrawable(color)
            }

            userCartProduct.selectedSize?.let {
                binding.productSizeBackground.visibility = View.VISIBLE
                binding.productSize.text = it
            }

            Glide.with(binding.productImg)
                .load(userCartProduct.product.images[0])
                .placeholder(R.drawable.shopping_placeholder)
                .into(binding.productImg)


        }


    }


}