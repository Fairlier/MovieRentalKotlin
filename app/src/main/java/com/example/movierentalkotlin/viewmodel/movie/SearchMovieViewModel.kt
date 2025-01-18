package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.util.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchMovieViewModel(val dao: MovieDao) : ViewModel() {

    var title = ""
    val releaseYear = MutableLiveData<String>("")
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

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Movie.TITLE to title.takeIf { it.isNotEmpty() },
            Constants.Movie.RELEASE_YEAR to releaseYear.value.toString().takeIf { it.isNotEmpty() },
            Constants.Movie.DIRECTOR to director.takeIf { it.isNotEmpty() },
            Constants.Movie.COUNTRY to country.takeIf { it.isNotEmpty() },
            Constants.Movie.DURATION to if (duration > 0.0) duration else null,
            Constants.Movie.RENTAL_COST to if (rentalCost > 0.0) rentalCost else null,
            Constants.Movie.AVERAGE_RATING to if (averageRating > 0.0) averageRating else null
        )
        _navigateToCatalogAfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogAfterSearch.value = false
    }

    fun showDatePicker(field: String) {
        _showDatePickerForField.value = field
    }

    fun onDateSelected(year: Int, month: Int, day: Int, field: String) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }

        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)

        when (field) {
            "releaseYear" -> releaseYear.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}