package com.test.supermarket

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.supermarket.databinding.ItemListBinding

class ListAdapter(): ListAdapter<Item, com.test.supermarket.ListAdapter.ViewHolder>(Diffcallback) {

    companion object Diffcallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    lateinit var onItemClickListener: (Item) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            //binding.miniItemImage.load(item.thumbnail)
            binding.itemName.text = item.name.capitalize()
            binding.itemPrice.text = item.price.toString() + "$"

            binding.executePendingBindings()

            binding.root.setOnClickListener(){

                if (::onItemClickListener.isInitialized){

                    onItemClickListener(item)

                }else{
                    Log.e("ERROR","Not Initialized")
                }
            }

        }
    }
}
