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

    @Insert
    suspend fun insertAll(employees: List<Employee>)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employees WHERE id = :id")
    fun getById(id: Long): LiveData<Employee>

    @Query("SELECT * FROM employees ORDER BY id DESC")
    fun getAll(): LiveData<List<Employee>>

    @Query("""
        SELECT * FROM employees
        WHERE (:fullName IS NULL OR full_name LIKE '%' || :fullName || '%')
        AND (:dateOfBirth IS NULL OR date_of_birth LIKE '%' || :dateOfBirth || '%')
        AND (:address IS NULL OR address LIKE '%' || :address || '%')
        AND (:phoneNumber IS NULL OR phone_number LIKE '%' || :phoneNumber|| '%')
        AND (:dateOfHire IS NULL OR date_of_hire LIKE '%' || :dateOfHire|| '%')
        AND (:dateOfDismissal IS NULL OR date_of_dismissal LIKE '%' || :dateOfDismissal|| '%')
        AND (:salary IS NULL OR salary = :salary)
    """)
    fun search(
        fullName: String?,
        dateOfBirth: String?,
        address: String?,
        phoneNumber: String?,
        dateOfHire: String?,
        dateOfDismissal: String?,
        salary: Double?
    ): LiveData<List<Employee>>
}