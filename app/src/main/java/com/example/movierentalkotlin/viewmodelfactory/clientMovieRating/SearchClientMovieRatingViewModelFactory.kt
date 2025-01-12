package com.example.movierentalkotlin.viewmodelfactory.clientMovieRating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.clientMovieRating.SearchClientMovieRatingViewModel

class SearchClientMovieRatingViewModelFactory(private val clientMovieRatingDao: ClientMovieRatingDao,
                                              private val clientDao: ClientDao,
                                              private val movieDao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchClientMovieRatingViewModel::class.java)) {
            return SearchClientMovieRatingViewModel(clientMovieRatingDao, clientDao, movieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}