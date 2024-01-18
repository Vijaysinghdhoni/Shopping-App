package com.vijaydhoni.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.data.model.Category
import com.vijaydhoni.shoppingapp.databinding.SearchCategroyRvItemBinding

class SearchCategoryRvAdapter :
    RecyclerView.Adapter<SearchCategoryRvAdapter.SearchCategoryRvViewHolder>() {


    val callback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCategoryRvViewHolder {
        return SearchCategoryRvViewHolder(
            SearchCategroyRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchCategoryRvViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.bind(category)
    }

    var onClick: ((Category) -> Unit)? = null

    inner class SearchCategoryRvViewHolder(private val binding: SearchCategroyRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name

            binding.imgCategory.setImageResource(category.image)

            binding.root.setOnClickListener {
                onClick?.invoke(category)
            }

        }


    }

}