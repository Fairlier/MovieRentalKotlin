package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao

class ViewMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    val durationAsString = MutableLiveData<String>()
    val rentalCostAsString = MutableLiveData<String>()

    private val _navigateToEdit = MutableLiveData<Boolean>(false)
    val navigateToEdit: LiveData<Boolean> get() = _navigateToEdit

    val movie = dao.getById(id)

    init {
        movie.observeForever { movie ->
            if (movie != null) {
                durationAsString.value = movie.duration.toString()
                rentalCostAsString.value = movie.rentalCost.toString()
            }
        }
    }

    fun onNavigatedToEdit() {
        _navigateToEdit.value = false
    }
}