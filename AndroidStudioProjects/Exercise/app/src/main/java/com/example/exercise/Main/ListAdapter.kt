package com.example.exercise.Main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise.databinding.ListItemsBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.exercise.Item

class ListAdapter(): ListAdapter<Item, com.example.exercise.Main.ListAdapter.ViewHolder>(
    Diffcallback
) {

    companion object Diffcallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }

    inner class ViewHolder(private val binding: ListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            binding.listId.text = item.listId.toString()
            binding.listName.text = item.name

            binding.executePendingBindings()

        }
    }
}
