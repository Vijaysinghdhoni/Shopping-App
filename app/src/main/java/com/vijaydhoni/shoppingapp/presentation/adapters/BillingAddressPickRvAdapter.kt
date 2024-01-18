package com.vijaydhoni.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.databinding.ButtonTabLayoutBinding

class BillingAddressPickRvAdapter :
    RecyclerView.Adapter<BillingAddressPickRvAdapter.BillingAddressRvViewholder>() {

    private var selectedPosition = -1

    private val callback = object : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.addressLocation == newItem.addressLocation
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingAddressRvViewholder {
        return BillingAddressRvViewholder(
            ButtonTabLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BillingAddressRvViewholder, position: Int) {
        val address = differ.currentList[position]
        holder.bind(address, position)

        holder.binding.outlinedButton.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onclick?.invoke(address)
        }

    }


    var onclick: ((Address) -> Unit)? = null

    inner class BillingAddressRvViewholder(val binding: ButtonTabLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(address: Address, position: Int) {
            binding.outlinedButton.text = address.addressLocation

            if (selectedPosition == position) {
                binding.outlinedButton.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.g_blue
                    )
                )
                binding.outlinedButton.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.g_white
                    )
                )
            }

        }


    }

}