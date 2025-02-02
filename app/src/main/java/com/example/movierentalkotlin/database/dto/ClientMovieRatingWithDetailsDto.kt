package com.example.movierentalkotlin.database.dto

data class ClientMovieRatingWithDetailsDto(

    val ratingId: Long,
    val clientId: Long,
    val movieId: Long,
    val rating: Double,
    val comment: String,

    val clientFullName: String,
    val clientDateOfBirth: String,
    val clientAddress: String,
    val clientPhoneNumber: String,
    val clientDateOfRegistration: String,
    val clientImageUrl: String?,

    val movieTitle: String,
    val movieReleaseYear: String,
    val movieDirector: String,
    val movieCountry: String,
    val movieDuration: Double,
    val movieRentalCost: Double,
    val movieAverageRating: Double,
    val movieDescription: String,
    val movieImageUrl: String?
)
