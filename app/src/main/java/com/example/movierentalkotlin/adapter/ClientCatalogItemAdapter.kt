package com.example.movierentalkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.databinding.ClientCatalogItemBinding
import com.example.movierentalkotlin.diffutil.ClientDiffItemCallback

class ClientCatalogItemAdapter(val clickListener: (id: Long) -> Unit)
    : ListAdapter<Client, ClientCatalogItemAdapter.ItemViewHolder>(ClientDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder = ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ItemViewHolder(val binding: ClientCatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientCatalogItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: Client, clickListener: (id: Long) -> Unit) {
            binding.client = item
            binding.root.setOnClickListener { clickListener(item.id) }
        }
    }
}