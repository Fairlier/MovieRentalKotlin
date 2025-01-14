package com.example.movierentalkotlin.viewmodelfactory.movieRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.viewmodel.clientMovieRating.ViewClientMovieRatingViewModel

class ViewMovieRentalViewModelFactory(private val id: Long,
                                      private val clientMovieRatingDao: ClientMovieRatingDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewClientMovieRatingViewModel::class.java)) {
            return ViewClientMovieRatingViewModel(id, clientMovieRatingDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}