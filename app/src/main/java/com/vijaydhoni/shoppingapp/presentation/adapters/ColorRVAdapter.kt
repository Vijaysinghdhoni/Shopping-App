package com.vijaydhoni.shoppingapp.presentation.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.databinding.ColorRvItemBinding

class ColorRVAdapter : RecyclerView.Adapter<ColorRVAdapter.ColorRvViewholder>() {

    private var selectedPosition = -1

    val callback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorRvViewholder {
        val binding = ColorRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorRvViewholder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ColorRvViewholder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onClick?.invoke(color)
        }
    }

    var onClick: ((color: Int) -> Unit)? = null

    inner class ColorRvViewholder(private val binding: ColorRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(color: Int, position: Int) {

            val imageDrawable = ColorDrawable(color)
            binding.imageColor.setImageDrawable(imageDrawable)
            if (position == selectedPosition) {
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                    imagepicked.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                    imagepicked.visibility = View.INVISIBLE
                }
            }

        }

    }

}