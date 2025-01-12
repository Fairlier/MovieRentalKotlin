package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.util.Constants

class SearchMovieSelectionViewModel(val dao: MovieDao) : ViewModel() {

    var title = ""
    var releaseYear = ""
    var director = ""
    var country = ""
    var duration = 0.0
    var rentalCost = 0.0
    var averageRating = 0.0

    var durationAsString: String get() = duration.toString()
        set(value) {
            duration = value.toDoubleOrNull() ?: 0.0
        }

    var rentalCostAsString: String get() = rentalCost.toString()
        set(value) {
            rentalCost = value.toDoubleOrNull() ?: 0.0
        }

    var averageRatingAsString: String get() = averageRating.toString()
        set(value) {
            averageRating = value.toDoubleOrNull() ?: 0.0
        }

    private val _navigateToCatalogAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogAfterSearch

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Movie.TITLE to title.takeIf { it.isNotEmpty() },
            Constants.Movie.RELEASE_YEAR to releaseYear.takeIf { it.isNotEmpty() },
            Constants.Movie.DIRECTOR to director.takeIf { it.isNotEmpty() },
            Constants.Movie.COUNTRY to country.takeIf { it.isNotEmpty() },
            Constants.Movie.DURATION to if (duration > 0) duration else null,
            Constants.Movie.RENTAL_COST to if (rentalCost > 0) rentalCost else null,
            Constants.Movie.AVERAGE_RATING to if (averageRating > 0) averageRating else null
        )
        _navigateToCatalogAfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogAfterSearch.value = false
    }
}