package com.example.movierentalkotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movierentalkotlin.util.Constants

@Entity(
    tableName = Constants.ClientMovieRating.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = [Constants.Client.ID],
        childColumns = [Constants.ClientMovieRating.CLIENT_ID]
    ), ForeignKey(
        entity = Movie::class,
        parentColumns = [Constants.Movie.ID],
        childColumns = [Constants.ClientMovieRating.MOVIE_ID]
    )],
    indices = [
        Index(value = [Constants.ClientMovieRating.CLIENT_ID]),
        Index(value = [Constants.ClientMovieRating.MOVIE_ID])
    ]
)
data class ClientMovieRating(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ClientMovieRating.ID)
    var id: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.CLIENT_ID)
    var clientId: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.MOVIE_ID)
    var movieId: Long = 0L,

    @ColumnInfo(name = Constants.ClientMovieRating.RATING)
    var rating: Double = 0.0,

    @ColumnInfo(name = Constants.ClientMovieRating.COMMENT)
    var comment: String = ""
)