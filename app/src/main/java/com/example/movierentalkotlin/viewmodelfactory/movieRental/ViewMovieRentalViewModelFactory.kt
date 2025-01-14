package com.example.movierentalkotlin.viewmodelfactory.movieRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.viewmodel.movieRental.ViewMovieRentalViewModel

class ViewMovieRentalViewModelFactory(private val id: Long,
                                      private val movieRentalDao: MovieRentalDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewMovieRentalViewModel::class.java)) {
            return ViewMovieRentalViewModel(id, movieRentalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}