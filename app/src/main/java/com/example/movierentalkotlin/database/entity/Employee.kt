package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(tableName = Constants.Employee.TABLE_NAME)
data class Employee(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Employee.ID)
    val id: Long = 0L,

    @ColumnInfo(name = Constants.Employee.FULL_NAME)
    val fullName: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_BIRTH)
    val dateOfBirth: String = "",

    @ColumnInfo(name = Constants.Employee.ADDRESS)
    val address: String = "",

    @ColumnInfo(name = Constants.Employee.PHONE_NUMBER)
    val phoneNumber: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_HIRE)
    val dateOfHire: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_DISMISSAL)
    val dateOfDismissal: String = "",

    @ColumnInfo(name = Constants.Employee.SALARY)
    val salary: Double = 0.0,

    @ColumnInfo(name = Constants.Movie.IMAGE)
    val image: String? = null
)