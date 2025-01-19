package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.ClientMovieRating
import com.example.movierentalkotlin.util.ClientMovieRatingData
import kotlinx.coroutines.launch

class InsertClientMovieRatingViewModel(val clientMovieRatingDao: ClientMovieRatingDao,
                                       val clientDao: ClientDao,
                                       val movieDao: MovieDao) : ViewModel() {

    val clientMovieRatingData = MutableLiveData<ClientMovieRatingData>(ClientMovieRatingData())

    var ratingAsString: String
        get() = clientMovieRatingData.value?.rating?.toString() ?: ""
        set(value) {
            val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
            updatedData.rating = (value.toDoubleOrNull() ?: 0.0).coerceIn(0.0, 10.0)
            clientMovieRatingData.value = updatedData
        }

    private val _navigateToCatalogAfterInsert = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterInsert: LiveData<Boolean> get() = _navigateToCatalogAfterInsert

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
        val clientLiveData = clientDao.getById(id)
        clientLiveData.observeForever { client ->
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
        val movieLiveData = movieDao.getById(id)
        movieLiveData.observeForever { movie ->
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

    fun insert() {
        viewModelScope.launch {
            val currentData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
            if (currentData.clientId == null || currentData.movieId == null) {
                _showValidationError.value = true
                return@launch
            }

            val clientMovieRating = ClientMovieRating(
                clientId = currentData.clientId!!,
                movieId = currentData.movieId!!,
                rating = currentData.rating.coerceIn(0.0, 10.0),
                comment = currentData.comment
            )
            clientMovieRatingDao.insert(clientMovieRating)

            val rawAverageRating = clientMovieRatingDao.calculateAverageRating(currentData.movieId!!)

            if (rawAverageRating != null) {
                val normalizedAverageRating = rawAverageRating.coerceIn(0.0, 10.0)
                movieDao.updateAverageRating(currentData.movieId!!, normalizedAverageRating)
            }

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