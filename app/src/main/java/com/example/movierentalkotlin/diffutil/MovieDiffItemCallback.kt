package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.entity.Movie

class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie)
            = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie)
            = (oldItem == newItem)
}