package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movierentalkotlin.database.entity.Movie

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Long): LiveData<Movie>

    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun getAll(): LiveData<List<Movie>>

    @Query("""
        SELECT * FROM movies
        WHERE (:title IS NULL OR title LIKE '%' || :title || '%')
        AND (:releaseYear IS NULL OR release_year LIKE '%' || :releaseYear || '%')
        AND (:director IS NULL OR director LIKE '%' || :director || '%')
        AND (:country IS NULL OR country LIKE '%' || :country|| '%')
        AND (:duration IS NULL OR duration = :duration)
        AND (:rentalCost IS NULL OR rental_cost = :rentalCost)
        AND (:averageRating IS NULL OR average_rating = :averageRating)
    """)
    fun search(
        title: String?,
        releaseYear: String?,
        director: String?,
        country: String?,
        duration: Double?,
        rentalCost: Double?,
        averageRating: Double?
    ): LiveData<List<Movie>>

    @Query("""
        UPDATE movies 
        SET average_rating = :newAverage 
        WHERE id = :movieId
    """)
    suspend fun updateAverageRating(movieId: Long, newAverage: Double)
}