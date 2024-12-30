package com.example.movierentalkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.databinding.MovieCatalogItemBinding
import com.example.movierentalkotlin.diffutil.MovieDiffItemCallback

class MovieCatalogItemAdapter(val clickListener: (id: Long) -> Unit)
    : ListAdapter<Movie, MovieCatalogItemAdapter.ItemViewHolder>(MovieDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder = ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ItemViewHolder(val binding: MovieCatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieCatalogItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: Movie, clickListener: (id: Long) -> Unit) {
            binding.movie = item
            binding.root.setOnClickListener { clickListener(item.id) }
        }
    }
}