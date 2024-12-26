package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.viewmodel.EditMovieViewModel

class EditMovieViewModelFactory(private val id: Long, private val dao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMovieViewModel::class.java)) {
            return EditMovieViewModel(id, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}