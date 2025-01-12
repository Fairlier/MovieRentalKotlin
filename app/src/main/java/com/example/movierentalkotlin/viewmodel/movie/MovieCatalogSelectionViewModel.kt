package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.util.Constants

class MovieCatalogSelectionViewModel(val dao: MovieDao) : ViewModel() {

    private val _catalog = MutableLiveData<List<Movie>>()
    val catalog: LiveData<List<Movie>> get() = _catalog

    private val _navigateToBack = MutableLiveData<Long?>()
    val navigateToBack: LiveData<Long?> get() = _navigateToBack

    fun onCatalogItemClicked(id: Long) {
        _navigateToBack.value = id
    }

    fun onCatalogItemNavigated() {
        _navigateToBack.value = null
    }

    fun setFilters(filters: Map<String, Any?>) {
        search(filters)
    }

    init {
        search(emptyMap())
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