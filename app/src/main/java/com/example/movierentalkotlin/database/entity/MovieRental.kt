package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    )],
    indices = [
        Index(value = [Constants.MovieRental.CLIENT_ID]),
        Index(value = [Constants.MovieRental.EMPLOYEE_ID]),
        Index(value = [Constants.MovieRental.MOVIE_ID])
    ]
)
data class MovieRental(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.MovieRental.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.CLIENT_ID)
    var clientId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.EMPLOYEE_ID)
    var employeeId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.MOVIE_ID)
    var movieId: Long = 0L,

    @ColumnInfo(name = Constants.MovieRental.DATE_OF_RECEIPT)
    var dateOfReceipt: String = "",

    @ColumnInfo(name = Constants.MovieRental.DATE_OF_RETURN)
    var dateOfReturn: String = ""
)