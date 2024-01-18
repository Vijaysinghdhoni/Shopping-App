package com.vijaydhoni.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.databinding.SizesRvItemBinding

class SizesRVAdapter : RecyclerView.Adapter<SizesRVAdapter.SizesRvViewholder>() {

    private var selectedPosition = -1

    val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesRvViewholder {
        return SizesRvViewholder(
            SizesRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SizesRvViewholder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onClick?.invoke(size)
        }
    }

    var onClick: ((String) -> Unit)? = null

    inner class SizesRvViewholder(private val binding: SizesRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(size: String, position: Int) {
            binding.sizeText.text = size

            if (position == selectedPosition) {
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                }
            }


        }

    }
}

