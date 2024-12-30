package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.entity.MovieRental

class MovieRentalDiffItemCallback : DiffUtil.ItemCallback<MovieRental>() {

    override fun areItemsTheSame(oldItem: MovieRental, newItem: MovieRental)
            = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: MovieRental, newItem: MovieRental)
            = (oldItem == newItem)
}