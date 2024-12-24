package com.example.movierentalkotlin.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movierentalkotlin.database.entity.Employee

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insert(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employees WHERE id = :id")
    fun getById(id: Long): LiveData<Employee>

    @Query("SELECT * FROM employees ORDER BY id DESC")
    fun getAll(): LiveData<List<Employee>>
}