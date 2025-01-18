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

    @Insert
    suspend fun insertAll(clients: List<Client>)

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

    @Query("SELECT * FROM clients WHERE id = :id")
    fun getById(id: Long): LiveData<Client>

    @Query("SELECT * FROM clients ORDER BY id DESC")
    fun getAll(): LiveData<List<Client>>

    @Query("""
        SELECT * FROM clients
        WHERE (:fullName IS NULL OR full_name LIKE '%' || :fullName || '%')
        AND (:dateOfBirth IS NULL OR date_of_birth LIKE '%' || :dateOfBirth || '%')
        AND (:address IS NULL OR address LIKE '%' || :address || '%')
        AND (:phoneNumber IS NULL OR phone_number LIKE '%' || :phoneNumber|| '%')
        AND (:dateOfRegistration IS NULL OR date_of_registration LIKE '%' || :dateOfRegistration|| '%')
    """)
    fun search(
        fullName: String?,
        dateOfBirth: String?,
        address: String?,
        phoneNumber: String?,
        dateOfRegistration: String?
    ): LiveData<List<Client>>
}