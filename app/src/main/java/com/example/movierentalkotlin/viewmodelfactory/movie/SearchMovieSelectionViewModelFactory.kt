package com.example.movierentalkotlin.viewmodelfactory.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.movie.SearchMovieSelectionViewModel

class SearchMovieSelectionViewModelFactory(private val dao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMovieSelectionViewModel::class.java)) {
            return SearchMovieSelectionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}