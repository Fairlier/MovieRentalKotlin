package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.entity.ClientMovieRating

class ClientMovieRatingDiffItemCallback : DiffUtil.ItemCallback<ClientMovieRating>() {

    override fun areItemsTheSame(oldItem: ClientMovieRating, newItem: ClientMovieRating)
            = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: ClientMovieRating, newItem: ClientMovieRating)
            = (oldItem == newItem)
}