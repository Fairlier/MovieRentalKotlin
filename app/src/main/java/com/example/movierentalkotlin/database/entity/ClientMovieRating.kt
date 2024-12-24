package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(tableName = Constants.ClientMovieRating.TABLE_NAME)
data class ClientMovieRating(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ClientMovieRating.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.CLIENT_ID)
    val clientId: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.MOVIE_ID)
    val movieId: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.RATING)
    val rating: Double = 0.0,

    @ColumnInfo(name = Constants.ClientMovieRating.COMMENT)
    val comment: String = ""
)