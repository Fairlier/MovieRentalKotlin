package com.example.movierentalkotlin.util

data class MovieRentalData(

    var clientId: Long? = null,
    var employeeId: Long? = null,
    var movieId: Long? = null,
    var dateOfReceipt: String = "",
    var dateOfReturn: String = "",

    var clientFullName: String = "",
    var clientDateOfBirth: String = "",
    var clientAddress: String = "",
    var clientPhoneNumber: String = "",
    var clientDateOfRegistration: String = "",
    var clientImageUrl: String? = null,

    var employeeFullName: String = "",
    var employeeDateOfBirth: String = "",
    var employeeAddress: String = "",
    var employeePhoneNumber: String = "",
    var employeeDateOfHire: String = "",
    var employeeDateOfDismissal: String = "",
    var employeeSalary: Double = 0.0,
    var employeeImageUrl: String? = null,

    var movieTitle: String = "",
    var movieReleaseYear: String = "",
    var movieDirector: String = "",
    var movieCountry: String = "",
    var movieDuration: Double = 0.0,
    var movieRentalCost: Double = 0.0,
    var movieAverageRating: Double = 0.0,
    var movieImageUrl: String? = null
)
