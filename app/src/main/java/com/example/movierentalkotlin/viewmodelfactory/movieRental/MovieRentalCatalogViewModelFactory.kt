package com.example.movierentalkotlin.viewmodelfactory.movieRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.viewmodel.movieRental.MovieRentalCatalogViewModel

class MovieRentalCatalogViewModelFactory(private val dao: MovieRentalDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieRentalCatalogViewModel::class.java)) {
            return MovieRentalCatalogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}