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

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Long): LiveData<Movie>

    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun getAll(): LiveData<List<Movie>>
}