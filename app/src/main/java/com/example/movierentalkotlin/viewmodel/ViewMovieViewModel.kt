package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.MovieDao
import kotlinx.coroutines.launch

class ViewMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    private val _navigateToCatalog = MutableLiveData<Boolean>(false)
    val navigateToCatalog: LiveData<Boolean> get() = _navigateToCatalog

    val movie = dao.getById(id)

    fun updateMovie() {
        viewModelScope.launch {
            dao.update(movie.value!!)
            _navigateToCatalog.value = true
        }
    }

    fun deleteMovie() {
        viewModelScope.launch {
            dao.delete(movie.value!!)
            _navigateToCatalog.value = true
        }
    }

    fun onNavigatedToCatalog() {
        _navigateToCatalog.value = false
    }
}