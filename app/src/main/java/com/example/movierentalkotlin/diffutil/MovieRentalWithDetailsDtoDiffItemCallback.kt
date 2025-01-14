package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.dto.MovieRentalWithDetailsDto

class MovieRentalWithDetailsDtoDiffItemCallback : DiffUtil.ItemCallback<MovieRentalWithDetailsDto>() {

    override fun areItemsTheSame(oldItem: MovieRentalWithDetailsDto, newItem: MovieRentalWithDetailsDto)
            = (oldItem.rentalId == newItem.rentalId)

    override fun areContentsTheSame(oldItem: MovieRentalWithDetailsDto, newItem: MovieRentalWithDetailsDto)
            = (oldItem == newItem)
}