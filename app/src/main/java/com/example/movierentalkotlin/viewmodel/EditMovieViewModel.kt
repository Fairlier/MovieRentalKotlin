package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.MovieDao
import kotlinx.coroutines.launch

class EditMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    val durationAsString = MutableLiveData<String>()
    val rentalCostAsString = MutableLiveData<String>()

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    val movie = dao.getById(id)

    init {
        movie.observeForever { movie ->
            if (movie != null) {
                durationAsString.value = movie.duration.toString()
                rentalCostAsString.value = movie.rentalCost.toString()
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            val movieToUpdate = movie.value
            if (movieToUpdate != null) {
                movieToUpdate.duration = durationAsString.value?.toDoubleOrNull() ?: 0.0
                movieToUpdate.rentalCost = rentalCostAsString.value?.toDoubleOrNull() ?: 0.0
                dao.update(movieToUpdate)
                _navigateToViewAfterUpdate.value = true
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            dao.delete(movie.value!!)
            _navigateToViewAfterDelete.value = true
        }
    }

    fun onNavigatedToViewAfterUpdate() {
        _navigateToViewAfterUpdate.value = false
    }

    fun onNavigatedToViewAfterDelete() {
        _navigateToViewAfterDelete.value = false
    }
}