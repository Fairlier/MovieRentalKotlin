package com.example.movierentalkotlin.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.database.entity.ClientMovieRating
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.util.Constants

data class ClientMovieRatingWithDetailsDto(

    @Embedded
    val clientMovieRating: ClientMovieRating,

    @Relation(
        parentColumn = Constants.ClientMovieRating.CLIENT_ID,
        entityColumn = Constants.Client.ID
    )
    val client: Client,

    @Relation(
        parentColumn = Constants.ClientMovieRating.MOVIE_ID,
        entityColumn = Constants.Movie.ID
    )
    val movie: Movie
)
