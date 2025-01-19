package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dto.ClientMovieRatingWithDetailsDto
import com.example.movierentalkotlin.util.Constants

class ClientMovieRatingCatalogViewModel(val dao: ClientMovieRatingDao) : ViewModel() {

    private val _catalog = MutableLiveData<List<ClientMovieRatingWithDetailsDto>>()
    val catalog: LiveData<List<ClientMovieRatingWithDetailsDto>> get() = _catalog

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
            dao.getAllWithDetails()
        } else {
            dao.searchWithDetails(
                filters[Constants.ClientMovieRating.CLIENT_ID] as? Long,
                filters[Constants.ClientMovieRating.MOVIE_ID] as? Long,
                filters[Constants.ClientMovieRating.RATING] as? Double
            )
        }
        itemsLiveData.observeForever { items ->
            _catalog.postValue(items)
        }
    }
}