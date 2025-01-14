package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movierentalkotlin.database.dto.ClientMovieRatingWithDetailsDto
import com.example.movierentalkotlin.database.entity.ClientMovieRating

@Dao
interface ClientMovieRatingDao {

    @Insert
    suspend fun insert(clientMovieRating: ClientMovieRating)

    @Update
    suspend fun update(clientMovieRating: ClientMovieRating)

    @Delete
    suspend fun delete(clientMovieRating: ClientMovieRating)

    @Query("SELECT * FROM client_movie_ratings WHERE id = :id")
    fun getById(id: Long): LiveData<ClientMovieRating>

    @Query("SELECT * FROM client_movie_ratings ORDER BY id DESC")
    fun getAll(): LiveData<List<ClientMovieRating>>

    @Query(
        """
        SELECT * FROM client_movie_ratings
        WHERE (:clientId IS NULL OR client_id = :clientId)
        AND (:movieId IS NULL OR movie_id = :movieId)
        AND (:rating IS NULL OR rating = :rating)
    """
    )
    fun search(
        clientId: Long?,
        movieId: Long?,
        rating: Double?
    ): LiveData<List<ClientMovieRating>>

    @Transaction
    @Query("""
        SELECT cmr.id AS ratingId, 
            cmr.id AS ratingId,
            cmr.client_id AS clientId,
            cmr.movie_id AS movieId,
            cmr.rating AS rating,
            cmr.comment AS comment,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM client_movie_ratings AS cmr
        INNER JOIN clients AS c ON cmr.client_id = c.id
        INNER JOIN movies AS m ON cmr.movie_id = m.id
        WHERE (cmr.id = :id)
    """)
    fun getByIdWithDetails(id: Long): LiveData<ClientMovieRatingWithDetailsDto>

    @Transaction
    @Query("""
        SELECT cmr.id AS ratingId, 
            cmr.id AS ratingId,
            cmr.client_id AS clientId,
            cmr.movie_id AS movieId,
            cmr.rating AS rating,
            cmr.comment AS comment,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM client_movie_ratings AS cmr
        INNER JOIN clients AS c ON cmr.client_id = c.id
        INNER JOIN movies AS m ON cmr.movie_id = m.id
    """)
    fun getAllWithDetails(): LiveData<List<ClientMovieRatingWithDetailsDto>>

    @Transaction
    @Query("""
        SELECT cmr.id AS ratingId, 
            cmr.id AS ratingId,
            cmr.client_id AS clientId,
            cmr.movie_id AS movieId,
            cmr.rating AS rating,
            cmr.comment AS comment,
            c.full_name AS clientFullName,
            c.date_of_birth AS clientDateOfBirth,
            c.address AS clientAddress,
            c.phone_number AS clientPhoneNumber,
            c.date_of_registration AS clientDateOfRegistration,
            c.image_url AS clientImageUrl,
            m.title AS movieTitle,
            m.release_year AS movieReleaseYear,
            m.director AS movieDirector,
            m.country AS movieCountry,
            m.duration AS movieDuration,
            m.rental_cost AS movieRentalCost,
            m.average_rating AS movieAverageRating,
            m.description AS movieDescription,
            m.image_url AS movieImageUrl
        FROM client_movie_ratings AS cmr
        INNER JOIN clients AS c ON cmr.client_id = c.id
        INNER JOIN movies AS m ON cmr.movie_id = m.id
        WHERE (:clientId IS NULL OR cmr.client_id = :clientId)
        AND (:movieId IS NULL OR cmr.movie_id = :movieId)
        AND (:rating IS NULL OR cmr.rating = :rating)
    """)
    fun searchWithDetails(
        clientId: Long?,
        movieId: Long?,
        rating: Double?
    ): LiveData<List<ClientMovieRatingWithDetailsDto>>
}