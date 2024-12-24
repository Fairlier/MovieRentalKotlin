package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movierentalkotlin.database.entity.Client

@Dao
interface ClientDao {

    @Insert
    suspend fun insert(client: Client)

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

    @Query("SELECT * FROM clients WHERE id = :id")
    fun getById(id: Long): LiveData<Client>

    @Query("SELECT * FROM clients ORDER BY id DESC")
    fun getAll(): LiveData<List<Client>>
}