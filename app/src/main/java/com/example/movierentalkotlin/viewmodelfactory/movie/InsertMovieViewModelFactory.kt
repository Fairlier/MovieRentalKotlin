package com.example.movierentalkotlin.viewmodelfactory.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.movie.InsertMovieViewModel

class InsertMovieViewModelFactory(private val dao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InsertMovieViewModel::class.java)) {
            return InsertMovieViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}