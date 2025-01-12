package com.example.movierentalkotlin.util

data class ClientMovieRatingData(

    var clientId: Long? = null,
    var movieId: Long? = null,
    var rating: Double = 0.0,
    var comment: String = "",

    var clientFullName: String = "",
    var clientDateOfBirth: String = "",
    var clientAddress: String = "",
    var clientPhoneNumber: String = "",
    var clientDateOfRegistration: String = "",
    var clientImageUrl: String? = null,

    var movieTitle: String = "",
    var movieReleaseYear: String = "",
    var movieDirector: String = "",
    var movieCountry: String = "",
    var movieDuration: Double = 0.0,
    var movieRentalCost: Double = 0.0,
    var movieAverageRating: Double = 0.0,
    var movieImageUrl: String? = null
)
