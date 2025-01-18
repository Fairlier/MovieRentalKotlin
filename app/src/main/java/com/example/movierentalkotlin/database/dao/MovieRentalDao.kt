package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movierentalkotlin.database.dto.MovieRentalWithDetailsDto
import com.example.movierentalkotlin.database.entity.MovieRental

@Dao
interface MovieRentalDao {

    @Insert
    suspend fun insert(movieRental: MovieRental)

    @Insert
    suspend fun insertAll(movieRentals: List<MovieRental>)

    @Update
    suspend fun update(movieRentals: MovieRental)

    @Delete
    suspend fun delete(movieRentals: MovieRental)

    @Query("SELECT * FROM movie_rentals WHERE id = :id")
    fun getById(id: Long): LiveData<MovieRental>

    @Query("SELECT * FROM movie_rentals ORDER BY id DESC")
    fun getAll(): LiveData<List<MovieRental>>

    @Query("""
        SELECT * FROM movie_rentals
        WHERE (:clientId IS NULL OR client_id = :clientId)
        AND (:employeeId IS NULL OR employee_id = :employeeId)
        AND (:movieId IS NULL OR movie_id = :movieId)
        AND (:dateOfReceipt IS NULL OR date_of_receipt LIKE '%' || :dateOfReceipt || '%')
        AND (:dateOfReturn IS NULL OR date_of_return LIKE '%' || :dateOfReturn || '%')
    """)
    fun search(
        clientId: Long?,
        employeeId: Long?,
        movieId: Long?,
        dateOfReceipt: String?,
        dateOfReturn: String?
    ): LiveData<List<MovieRental>>

    @Transaction
    @Query("""
        SELECT mr.id AS rentalId,
            mr.client_id AS clientId,
            mr.employee_id AS employeeId,
            mr.movie_id AS movieId,
            mr.date_of_receipt AS dateOfReceipt,
            mr.date_of_return AS dateOfReturn,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            e.full_name AS employeeFullName,
            e.date_of_birth AS employeeDateOfBirth,
            e.address AS employeeAddress,
            e.phone_number AS employeePhoneNumber,
            e.date_of_hire AS employeeDateOfHire,
            e.date_of_dismissal AS employeeDateOfDismissal,
            e.salary AS employeeSalary,
            e.image_url AS employeeImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM movie_rentals AS mr
        INNER JOIN clients AS c ON mr.client_id = c.id
        INNER JOIN employees AS e ON mr.employee_id = e.id
        INNER JOIN movies AS m ON mr.movie_id = m.id
        WHERE (mr.id = :id)
    """)
    fun getByIdWithDetails(id: Long): LiveData<MovieRentalWithDetailsDto>

    @Transaction
    @Query("""
        SELECT mr.id AS rentalId,
            mr.client_id AS clientId,
            mr.employee_id AS employeeId,
            mr.movie_id AS movieId,
            mr.date_of_receipt AS dateOfReceipt,
            mr.date_of_return AS dateOfReturn,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            e.full_name AS employeeFullName,
            e.date_of_birth AS employeeDateOfBirth,
            e.address AS employeeAddress,
            e.phone_number AS employeePhoneNumber,
            e.date_of_hire AS employeeDateOfHire,
            e.date_of_dismissal AS employeeDateOfDismissal,
            e.salary AS employeeSalary,
            e.image_url AS employeeImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM movie_rentals AS mr
        INNER JOIN clients AS c ON mr.client_id = c.id
        INNER JOIN employees AS e ON mr.employee_id = e.id
        INNER JOIN movies AS m ON mr.movie_id = m.id
    """)
    fun getAllWithDetails(): LiveData<List<MovieRentalWithDetailsDto>>

    @Transaction
    @Query("""
        SELECT mr.id AS rentalId,
            mr.client_id AS clientId,
            mr.employee_id AS employeeId,
            mr.movie_id AS movieId,
            mr.date_of_receipt AS dateOfReceipt,
            mr.date_of_return AS dateOfReturn,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            e.full_name AS employeeFullName,
            e.date_of_birth AS employeeDateOfBirth,
            e.address AS employeeAddress,
            e.phone_number AS employeePhoneNumber,
            e.date_of_hire AS employeeDateOfHire,
            e.date_of_dismissal AS employeeDateOfDismissal,
            e.salary AS employeeSalary,
            e.image_url AS employeeImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM movie_rentals AS mr
        INNER JOIN clients AS c ON mr.client_id = c.id
        INNER JOIN employees AS e ON mr.employee_id = e.id
        INNER JOIN movies AS m ON mr.movie_id = m.id
        WHERE (:clientId IS NULL OR mr.client_id = :clientId)
        AND (:employeeId IS NULL OR mr.employee_id = :employeeId)
        AND (:movieId IS NULL OR mr.movie_id = :movieId)
        AND (:dateOfReceipt IS NULL OR date_of_receipt LIKE '%' || :dateOfReceipt || '%')
        AND (:dateOfReturn IS NULL OR date_of_return LIKE '%' || :dateOfReturn || '%')
    """)
    fun searchWithDetails(
        clientId: Long?,
        employeeId: Long?,
        movieId: Long?,
        dateOfReceipt: String?,
        dateOfReturn: String?
    ): LiveData<List<MovieRentalWithDetailsDto>>
}