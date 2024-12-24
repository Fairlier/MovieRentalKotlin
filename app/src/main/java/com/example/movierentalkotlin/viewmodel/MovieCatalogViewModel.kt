package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.Movie
import kotlinx.coroutines.launch

class MovieCatalogViewModel(val dao: MovieDao) : ViewModel() {

    private val _navigateToMovie = MutableLiveData<Long?>()

    var newMovieTitle = ""

    val movieCatalog = dao.getAll()

    val navigateToMovie: LiveData<Long?>
        get() = _navigateToMovie

    fun addMovie() {
        viewModelScope.launch {
            val movie = Movie()
            movie.title = newMovieTitle
            dao.insert(movie)
        }
    }

    fun onMovieClicked(taskId: Long) {
        _navigateToMovie.value = taskId
    }

    fun onMovieNavigated() {
        _navigateToMovie.value = null
    }
}