package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.database.dto.MovieRentalWithDetailsDto
import com.example.movierentalkotlin.util.Constants

class MovieRentalCatalogViewModel(val dao: MovieRentalDao) : ViewModel() {

    private val _navigateToView = MutableLiveData<Long?>()
    val navigateToView: LiveData<Long?> get() = _navigateToView

    private val _navigateToInsert = MutableLiveData<Boolean>(false)
    val navigateToInsert: LiveData<Boolean> get() = _navigateToInsert

    private val _catalog = MutableLiveData<List<MovieRentalWithDetailsDto>>()
    val catalog: LiveData<List<MovieRentalWithDetailsDto>> get() = _catalog

    fun onCatalogItemClicked(id: Long) {
        _navigateToView.value = id
    }

    fun onCatalogItemNavigated() {
        _navigateToView.value = null
    }

    fun insert() {
        _navigateToInsert.value = true
    }

    fun onNavigatedToInsert() {
        _navigateToInsert.value = false
    }

    init {
        search(emptyMap())
    }

    fun setFilters(filters: Map<String, Any?>) {
        search(filters)
    }

    private fun search(filters: Map<String, Any?>) {
        val itemsLiveData = if (filters.isEmpty()) {
            dao.getAllWithDetails()
        } else {
            dao.searchWithDetails(
                filters[Constants.MovieRental.CLIENT_ID] as? Long,
                filters[Constants.MovieRental.EMPLOYEE_ID] as? Long,
                filters[Constants.MovieRental.MOVIE_ID] as? Long,
                filters[Constants.MovieRental.DATE_OF_RECEIPT] as? String,
                filters[Constants.MovieRental.DATE_OF_RETURN] as? String
            )
        }
        itemsLiveData.observeForever { items ->
            _catalog.postValue(items)
        }
    }
}