package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(tableName = Constants.Client.TABLE_NAME)
data class Client(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Client.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.Client.FULL_NAME)
    val fullName: String = "",

    @ColumnInfo(name = Constants.Client.DATE_OF_BIRTH)
    val dateOfBirth: String = "",

    @ColumnInfo(name = Constants.Client.ADDRESS)
    val address: String = "",

    @ColumnInfo(name = Constants.Client.PHONE_NUMBER)
    val phoneNumber: String = "",

    @ColumnInfo(name = Constants.Client.DATE_OF_REGISTRATION)
    val dateOfRegistration: String = ""
)