package com.example.movierentalkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierentalkotlin.database.dto.MovieRentalWithDetailsDto
import com.example.movierentalkotlin.databinding.MovieRentalCatalogItemBinding
import com.example.movierentalkotlin.diffutil.MovieRentalWithDetailsDtoDiffItemCallback

class MovieRentalCatalogItemAdapter(val clickListener: (id: Long) -> Unit)
    : ListAdapter<MovieRentalWithDetailsDto, MovieRentalCatalogItemAdapter.ItemViewHolder>(MovieRentalWithDetailsDtoDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder = ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ItemViewHolder(val binding: MovieRentalCatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieRentalCatalogItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: MovieRentalWithDetailsDto, clickListener: (id: Long) -> Unit) {
            binding.movieRentalWithDetailsDto = item
            binding.root.setOnClickListener {
                clickListener(item.rentalId)
            }
        }
    }
}