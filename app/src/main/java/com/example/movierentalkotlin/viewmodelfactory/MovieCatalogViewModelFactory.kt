package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.MovieCatalogViewModel

class MovieCatalogViewModelFactory(private val dao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieCatalogViewModel::class.java)) {
            return MovieCatalogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}