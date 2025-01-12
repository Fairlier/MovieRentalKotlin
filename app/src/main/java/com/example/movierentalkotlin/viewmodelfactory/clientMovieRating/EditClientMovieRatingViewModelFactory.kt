package com.example.movierentalkotlin.viewmodelfactory.clientMovieRating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.clientMovieRating.EditClientMovieRatingViewModel

class EditClientMovieRatingViewModelFactory(private val clientMovieRatingDao: ClientMovieRatingDao,
                                            private val clientDao: ClientDao,
                                            private val movieDao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditClientMovieRatingViewModel::class.java)) {
            return EditClientMovieRatingViewModel(clientMovieRatingDao, clientDao, movieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}