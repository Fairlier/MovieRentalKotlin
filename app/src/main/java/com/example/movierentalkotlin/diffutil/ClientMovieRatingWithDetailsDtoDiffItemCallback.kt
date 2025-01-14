package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.dto.ClientMovieRatingWithDetailsDto

class ClientMovieRatingWithDetailsDtoDiffItemCallback : DiffUtil.ItemCallback<ClientMovieRatingWithDetailsDto>() {

    override fun areItemsTheSame(oldItem: ClientMovieRatingWithDetailsDto, newItem: ClientMovieRatingWithDetailsDto)
            = (oldItem.ratingId == newItem.ratingId)

    override fun areContentsTheSame(oldItem: ClientMovieRatingWithDetailsDto, newItem: ClientMovieRatingWithDetailsDto)
            = (oldItem == newItem)
}