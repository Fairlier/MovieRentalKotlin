package com.example.movierentalkotlin.database.dto

data class MovieRentalWithDetailsDto(

    val rentalId: Long,
    val clientId: Long,
    val employeeId: Long,
    val movieId: Long,
    val dateOfReceipt: String,
    val dateOfReturn: String,

    val clientFullName: String,
    val clientDateOfBirth: String,
    val clientAddress: String,
    val clientPhoneNumber: String,
    val clientDateOfRegistration: String,
    val clientImageUrl: String?,

    val employeeFullName: String,
    val employeeDateOfBirth: String,
    val employeeAddress: String,
    val employeePhoneNumber: String,
    val employeeDateOfHire: String,
    val employeeDateOfDismissal: String,
    val employeeSalary: Double,
    val employeeImageUrl: String?,

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
