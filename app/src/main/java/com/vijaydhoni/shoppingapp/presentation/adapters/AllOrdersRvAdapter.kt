package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.util.OrderStatus
import com.vijaydhoni.shoppingapp.data.util.getOrderStatus
import com.vijaydhoni.shoppingapp.databinding.AllOrdersRvItemLayoutBinding

class AllOrdersRvAdapter : RecyclerView.Adapter<AllOrdersRvAdapter.AllOrdersRvViewholder>() {

    private val callback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrdersRvViewholder {
        return AllOrdersRvViewholder(
            AllOrdersRvItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllOrdersRvViewholder, position: Int) {
        val order = differ.currentList[position]
        holder.bind(order)
        holder.itemView.setOnClickListener {
            itemClick?.invoke(order)
        }
    }

    var itemClick: ((Order) -> Unit)? = null

    inner class AllOrdersRvViewholder(private val allOrdersRvItemLayoutBinding: AllOrdersRvItemLayoutBinding) :
        RecyclerView.ViewHolder(allOrdersRvItemLayoutBinding.root) {

        fun bind(order: Order) {
            allOrdersRvItemLayoutBinding.orderId.text = "#${order.orderId}"
            allOrdersRvItemLayoutBinding.orderDate.text = order.date

            val colorDrawable = when (getOrderStatus(order.orderStatus)) {

                is OrderStatus.Ordered -> {
                    ColorDrawable(Color.YELLOW)
                }

                is OrderStatus.Confirmed -> {
                    ColorDrawable(Color.BLUE)
                }

                is OrderStatus.Canceled -> {
                    ColorDrawable(Color.RED)
                }

                is OrderStatus.Delivered -> {
                    ColorDrawable(Color.GREEN)
                }

                is OrderStatus.Shipped -> {
                    ColorDrawable(Color.GREEN)
                }

                is OrderStatus.Returned -> {
                    ColorDrawable(Color.RED)
                }

            }

            allOrdersRvItemLayoutBinding.orderTrackOrder.setImageDrawable(colorDrawable)
            allOrdersRvItemLayoutBinding.ordrDetail.setOnClickListener {
                itemClick?.invoke(order)
            }

        }

    }
}