package com.example.movierentalkotlin.viewmodelfactory.clientMovieRating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.viewmodel.clientMovieRating.ClientMovieRatingCatalogViewModel

class ClientMovieRatingCatalogViewModelFactory(private val dao: ClientMovieRatingDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientMovieRatingCatalogViewModel::class.java)) {
            return ClientMovieRatingCatalogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}