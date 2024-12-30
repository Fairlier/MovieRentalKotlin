package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(tableName = Constants.Employee.TABLE_NAME)
data class Employee(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Employee.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.Employee.FULL_NAME)
    var fullName: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_BIRTH)
    var dateOfBirth: String = "",

    @ColumnInfo(name = Constants.Employee.ADDRESS)
    var address: String = "",

    @ColumnInfo(name = Constants.Employee.PHONE_NUMBER)
    var phoneNumber: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_HIRE)
    var dateOfHire: String = "",

    @ColumnInfo(name = Constants.Employee.DATE_OF_DISMISSAL)
    var dateOfDismissal: String = "",

    @ColumnInfo(name = Constants.Employee.SALARY)
    var salary: Double = 0.0,

    @ColumnInfo(name = Constants.Employee.IMAGE_URL)
    var imageUrl: String? = null
)