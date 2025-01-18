package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.util.Constants

class MovieCatalogViewModel(val dao: MovieDao) : ViewModel() {

    private val _catalog = MutableLiveData<List<Movie>>()
    val catalog: LiveData<List<Movie>> get() = _catalog

    private val _navigateToView = MutableLiveData<Long?>()
    val navigateToView: LiveData<Long?> get() = _navigateToView

    private val _navigateToInsert = MutableLiveData<Boolean>(false)
    val navigateToInsert: LiveData<Boolean> get() = _navigateToInsert

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
            dao.getAll()
        } else {
            dao.search(
                filters[Constants.Movie.TITLE] as? String,
                filters[Constants.Movie.RELEASE_YEAR] as? String,
                filters[Constants.Movie.DIRECTOR] as? String,
                filters[Constants.Movie.COUNTRY] as? String,
                filters[Constants.Movie.DURATION] as? Double,
                filters[Constants.Movie.RENTAL_COST] as? Double,
                filters[Constants.Movie.AVERAGE_RATING] as? Double
            )
        }
        itemsLiveData.observeForever { items ->
            _catalog.postValue(items)
        }
    }
}