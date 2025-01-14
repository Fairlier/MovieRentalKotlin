package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.util.ClientMovieRatingData
import com.example.movierentalkotlin.util.Constants

class SearchClientMovieRatingViewModel(val clientMovieRatingDao: ClientMovieRatingDao,
                                       val clientDao: ClientDao,
                                       val movieDao: MovieDao) : ViewModel() {

    val clientMovieRatingData = MutableLiveData<ClientMovieRatingData>(ClientMovieRatingData())

    var ratingAsString: String
        get() = clientMovieRatingData.value?.rating?.toString() ?: ""
        set(value) {
            val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
            updatedData.rating = value.toDoubleOrNull() ?: 0.0
            clientMovieRatingData.value = updatedData
        }

    private val _navigateToCatalogAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogAfterSearch

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    private val _navigateToClientCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToClientCatalogSelection: LiveData<Boolean> get() = _navigateToClientCatalogSelection

    private val _navigateToMovieCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToMovieCatalogSelection: LiveData<Boolean> get() = _navigateToMovieCatalogSelection

    private val _showValidationError = MutableLiveData<Boolean>(false)
    val showValidationError: LiveData<Boolean> get() = _showValidationError

    fun initializationClientMovieRatingData(clientMovieRatingData: ClientMovieRatingData) {
        this.clientMovieRatingData.value = clientMovieRatingData.copy()
    }

    fun onClientCardClicked() {
        _navigateToClientCatalogSelection.value = true
    }

    fun onClientCardNavigated() {
        _navigateToClientCatalogSelection.value = false
    }

    fun onMovieCardClicked() {
        _navigateToMovieCatalogSelection.value = true
    }

    fun onMovieCardNavigated() {
        _navigateToMovieCatalogSelection.value = false
    }

    fun updateClient(id: Long) {
        val clientLiveDataDataMovie = clientDao.getById(id)
        clientLiveDataDataMovie.observeForever { client ->
            client?.let {
                val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
                updatedData.clientId = it.id
                updatedData.clientFullName = it.fullName
                updatedData.clientDateOfBirth = it.dateOfBirth
                updatedData.clientAddress = it.address
                updatedData.clientPhoneNumber = it.phoneNumber
                updatedData.clientDateOfRegistration = it.dateOfRegistration
                updatedData.clientImageUrl = it.imageUrl
                clientMovieRatingData.postValue(updatedData)
            }
        }
    }

    fun updateMovie(id: Long) {
        val liveDataMovie = movieDao.getById(id)
        liveDataMovie.observeForever { movie ->
            movie?.let {
                val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
                updatedData.movieId = it.id
                updatedData.movieTitle = it.title
                updatedData.movieReleaseYear = it.releaseYear
                updatedData.movieDirector = it.director
                updatedData.movieCountry = it.country
                updatedData.movieDuration = it.duration
                updatedData.movieRentalCost = it.rentalCost
                updatedData.movieAverageRating = it.averageRating
                updatedData.movieImageUrl = it.imageUrl
                clientMovieRatingData.postValue(updatedData)
            }
        }
    }

    fun search() {
        val currentData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
        _filters.value = mapOf(
            Constants.ClientMovieRating.CLIENT_ID to currentData.clientId,
            Constants.ClientMovieRating.MOVIE_ID to currentData.movieId,
            Constants.ClientMovieRating.RATING to if (currentData.rating > 0) currentData.rating else null
        )
        _navigateToCatalogAfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogAfterSearch.value = false
    }

    fun onValidationErrorShown() {
        _showValidationError.value = false
    }
}