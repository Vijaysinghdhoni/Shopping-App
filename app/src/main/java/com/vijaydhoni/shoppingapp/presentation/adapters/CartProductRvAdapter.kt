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
import com.vijaydhoni.shoppingapp.databinding.CartItemLayoutBinding

class CartProductRvAdapter : RecyclerView.Adapter<CartProductRvAdapter.CartProductRvViewHolder>() {

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

    var differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductRvViewHolder {
        return CartProductRvViewHolder(
            CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartProductRvViewHolder, position: Int) {
        val userCartproduct = differ.currentList[position]
        holder.bind(userCartproduct)

    }


    inner class CartProductRvViewHolder(private val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userCartProduct: UserCartProduct) {

            binding.productName.text = userCartProduct.product.productName

            userCartProduct.product.offerPercentage?.let {
                binding.productPrice.apply {
                    val discountAmount = userCartProduct.product.price * (it.div(100))
                    val offerPrice = userCartProduct.product.price - discountAmount
                    text = "Rs${offerPrice}"
                }
            }



            userCartProduct.selectedColor?.let {
                val imageDrawable = ColorDrawable(it)
                binding.productSelectedColor.setImageDrawable(imageDrawable)
            }

            binding.productQuantity.text = userCartProduct.quantity.toString()

            userCartProduct.selectedSize?.let {
                binding.circlSize.visibility = View.VISIBLE
                binding.productSize.text = it
            }


            Glide.with(binding.productImg)
                .load(userCartProduct.product.images[0])
                .placeholder(R.drawable.shopping_placeholder)
                .into(binding.productImg)

            binding.productRemove.setOnClickListener {
                onClickRemove?.invoke(userCartProduct)
            }

            binding.addProduct.setOnClickListener {
                onClickAdd?.invoke(userCartProduct)
            }

        }


    }

    var onClickRemove: ((UserCartProduct) -> Unit)? = null
    var onClickAdd: ((UserCartProduct) -> Unit)? = null

}

