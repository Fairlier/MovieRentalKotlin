package com.example.movierentalkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.database.entity.ClientMovieRating
import com.example.movierentalkotlin.database.entity.Employee
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.database.entity.MovieRental
import com.example.movierentalkotlin.util.Constants

@Database(
    entities = [
        Movie::class,
        Employee::class,
        Client::class,
        MovieRental::class,
        ClientMovieRating::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieRentalDatabase : RoomDatabase() {

    abstract val clientDao : ClientDao
    abstract val clientMovieRatingDao : ClientMovieRatingDao
    abstract val employeeDao : EmployeeDao
    abstract val movieDao : MovieDao
    abstract val movieRentalDao : MovieRentalDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRentalDatabase? = null
        fun getInstance(context: Context): MovieRentalDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieRentalDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}