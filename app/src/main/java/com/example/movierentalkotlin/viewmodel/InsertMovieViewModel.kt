package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.Movie
import kotlinx.coroutines.launch

class InsertMovieViewModel(val dao: MovieDao) : ViewModel() {

    var title = ""
    var releaseYear = ""
    var director = ""
    var country = ""
    var duration = 0.0
    var rentalCost = 0.0
    var averageRating = 0.0
    var description = ""
    var image: String? = null

    var durationAsString: String get() = duration.toString()
        set(value) {
            duration = value.toDoubleOrNull() ?: 0.0
        }

    var rentalCostAsString: String get() = rentalCost.toString()
        set(value) {
            rentalCost = value.toDoubleOrNull() ?: 0.0
        }

    private val _navigateToViewAfterInsert = MutableLiveData<Boolean>(false)
    val navigateToViewAfterInsert: LiveData<Boolean> get() = _navigateToViewAfterInsert

    fun insert() {
        viewModelScope.launch {
            val movie = Movie(
                title = title,
                releaseYear = releaseYear,
                director = director,
                country = country,
                duration = duration,
                rentalCost = rentalCost,
                averageRating = averageRating,
                description = description,
                image = image
            )
            dao.insert(movie)
            _navigateToViewAfterInsert.value = true
        }
    }

    fun onNavigatedToViewAfterInsert() {
        _navigateToViewAfterInsert.value = false
    }
}