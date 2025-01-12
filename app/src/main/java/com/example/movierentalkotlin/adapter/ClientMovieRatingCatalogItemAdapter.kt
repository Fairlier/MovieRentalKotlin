package com.example.movierentalkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierentalkotlin.database.dto.ClientMovieRatingWithDetailsDto
import com.example.movierentalkotlin.databinding.ClientMovieRatingCatalogItemBinding
import com.example.movierentalkotlin.diffutil.ClientMovieRatingWithDetailsDtoDiffItemCallback

class ClientMovieRatingCatalogItemAdapter(val clickListener: (id: Long) -> Unit)
    : ListAdapter<ClientMovieRatingWithDetailsDto, ClientMovieRatingCatalogItemAdapter.ItemViewHolder>(ClientMovieRatingWithDetailsDtoDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder = ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ItemViewHolder(val binding: ClientMovieRatingCatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientMovieRatingCatalogItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(item: ClientMovieRatingWithDetailsDto, clickListener: (id: Long) -> Unit) {
            binding.clientMovieRating = item.clientMovieRating
            binding.client = item.client
            binding.movie = item.movie
            binding.root.setOnClickListener {
                clickListener(item.clientMovieRating.id)
                println("clientMovieRatingId: ${item.clientMovieRating.id}")
                println("clientMovieRating: ${item.clientMovieRating.rating}")
                println("client: ${item.client.id}")
                println("movie: ${item.movie.id}")
            }
        }
    }
}