package com.example.earthquakemonitor.Main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.ListAdapter //pendiente con traerte el que no es de las dependencias, clase adapters en curso
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.R
import com.example.earthquakemonitor.databinding.ListItemBinding

class EqAdapter(private val context: Context): ListAdapter<Earthquake, EqAdapter.ViewHolder>(Diffcallback) {

    companion object Diffcallback: DiffUtil.ItemCallback<Earthquake>(){

        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return  oldItem == newItem
        }

    }

    lateinit var  onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context))
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val earthquake = getItem(position)
        holder.bind(earthquake)

    }

    inner class ViewHolder(private val binding:ListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(earthquake: Earthquake){

            binding.eqMag.text = context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.eqPlace.text = earthquake.place

            binding.executePendingBindings()
            binding.root.setOnClickListener{

                if (::onItemClickListener.isInitialized){

                    onItemClickListener(earthquake)
                }else{
                    Log.e("EqAdapter", "Lampda not Initialized")
                }
            }
        }
    }
}