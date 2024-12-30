package com.example.movierentalkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierentalkotlin.database.entity.Employee
import com.example.movierentalkotlin.databinding.EmployeeCatalogItemBinding
import com.example.movierentalkotlin.diffutil.EmployeeDiffItemCallback

class EmployeeCatalogItemAdapter(val clickListener: (id: Long) -> Unit)
    : ListAdapter<Employee, EmployeeCatalogItemAdapter.ItemViewHolder>(EmployeeDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder = ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ItemViewHolder(val binding: EmployeeCatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeCatalogItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: Employee, clickListener: (id: Long) -> Unit) {
            binding.employee = item
            binding.root.setOnClickListener { clickListener(item.id) }
        }
    }
}