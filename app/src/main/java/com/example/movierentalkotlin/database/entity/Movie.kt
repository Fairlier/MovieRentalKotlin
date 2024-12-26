package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(tableName = Constants.Movie.TABLE_NAME)
data class Movie(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Movie.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.Movie.TITLE)
    var title: String = "",

    @ColumnInfo(name = Constants.Movie.RELEASE_YEAR)
    var releaseYear: String = "",

    @ColumnInfo(name = Constants.Movie.DIRECTOR)
    var director: String = "",

    @ColumnInfo(name = Constants.Movie.COUNTRY)
    var country: String = "",

    @ColumnInfo(name = Constants.Movie.DURATION)
    var duration: Double = 0.0,

    @ColumnInfo(name = Constants.Movie.RENTAL_COST)
    var rentalCost: Double = 0.0,

    @ColumnInfo(name = Constants.Movie.AVERAGE_RATING)
    val averageRating: Double = 0.0,

    @ColumnInfo(name = Constants.Movie.DESCRIPTION)
    var description: String = "",

    @ColumnInfo(name = Constants.Movie.IMAGE)
    var image: String? = null
)