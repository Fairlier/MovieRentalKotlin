package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao

class ViewMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    private val _durationAsString = MutableLiveData<String>()
    val durationAsString: LiveData<String> = _durationAsString

    private val _averageRatingAsString = MutableLiveData<String>()
    val averageRatingAsString: LiveData<String> = _averageRatingAsString

    private val _rentalCostAsString = MutableLiveData<String>()
    val rentalCostAsString: LiveData<String> = _rentalCostAsString

    val movie = dao.getById(id)

    init {
        movie.observeForever { movie ->
            if (movie != null) {
                _durationAsString.value = movie.duration.toString()
                _averageRatingAsString.value = movie.averageRating.toString()
                _rentalCostAsString.value = movie.rentalCost.toString()
            }
        }
    }
}