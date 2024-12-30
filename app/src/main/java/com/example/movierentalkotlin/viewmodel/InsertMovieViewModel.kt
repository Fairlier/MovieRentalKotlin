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
    var imageUrl: String? = null

    var durationAsString: String get() = duration.toString()
        set(value) {
            duration = value.toDoubleOrNull() ?: 0.0
        }

    var rentalCostAsString: String get() = rentalCost.toString()
        set(value) {
            rentalCost = value.toDoubleOrNull() ?: 0.0
        }

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    private val _navigateToCatalogAfterInsert = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterInsert: LiveData<Boolean> get() = _navigateToCatalogAfterInsert

    private val _showValidationError = MutableLiveData<Boolean>(false)
    val showValidationError: LiveData<Boolean> get() = _showValidationError

    fun insert() {
        viewModelScope.launch {
            if (title.isBlank()
//                || releaseYear.isBlank() || director.isBlank() ||
//                country.isBlank() || duration <= 0.0 || rentalCost <= 0.0 || averageRating < 0.0 ||
//                description.isBlank()
            ) {
                _showValidationError.value = true
                return@launch
            }
                val movie = Movie(
                title = title,
                releaseYear = releaseYear,
                director = director,
                country = country,
                duration = duration,
                rentalCost = rentalCost,
                averageRating = averageRating,
                description = description,
                imageUrl = _currentImageUrl.value
            )
            dao.insert(movie)
            _navigateToCatalogAfterInsert.value = true
        }
    }

    fun onNavigatedToCatalogAfterInsert() {
        _navigateToCatalogAfterInsert.value = false
    }

    fun onValidationErrorShown() {
        _showValidationError.value = false
    }
}