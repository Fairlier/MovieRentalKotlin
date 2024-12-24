package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}