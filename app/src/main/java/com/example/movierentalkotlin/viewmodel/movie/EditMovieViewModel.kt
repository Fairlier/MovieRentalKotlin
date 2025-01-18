package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.MovieDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    val releaseYear = MutableLiveData<String>("")

    val _durationAsString = MutableLiveData<String>()

    val _rentalCostAsString = MutableLiveData<String>()

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    val movie = dao.getById(id)

    init {
        movie.observeForever { movie ->
            if (movie != null) {
                _durationAsString.value = movie.duration.toString()
                _rentalCostAsString.value = movie.rentalCost.toString()
                _currentImageUrl.value = movie.imageUrl
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            val itemToUpdate = movie.value
            if (itemToUpdate != null) {
                itemToUpdate.releaseYear = releaseYear.value.toString()
                itemToUpdate.duration = _durationAsString.value?.toDoubleOrNull() ?: 0.0
                itemToUpdate.rentalCost = _rentalCostAsString.value?.toDoubleOrNull() ?: 0.0
                itemToUpdate.imageUrl = _currentImageUrl.value.toString()
                dao.update(itemToUpdate)
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