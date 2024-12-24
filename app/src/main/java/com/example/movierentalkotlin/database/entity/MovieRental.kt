package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(
    tableName = Constants.MovieRental.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = [Constants.Client.ID],
        childColumns = [Constants.MovieRental.CLIENT_ID]
    ), ForeignKey(
        entity = Employee::class,
        parentColumns = [Constants.Employee.ID],
        childColumns = [Constants.MovieRental.EMPLOYEE_ID]
    ), ForeignKey(
        entity = Movie::class,
        parentColumns = [Constants.Movie.ID],
        childColumns = [Constants.MovieRental.MOVIE_ID]
    )]
)
data class MovieRental(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.MovieRental.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.CLIENT_ID)
    val clientId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.EMPLOYEE_ID)
    val employeeId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.MOVIE_ID)
    val movieId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.DATE_OF_RECEIPT)
    val dateOfReceipt: String = "",

    @ColumnInfo(name = Constants.MovieRental.DATE_OF_RETURN)
    val dateOfReturn: String = ""
)