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

class EditClientMovieRatingViewModel(val id: Long,
                                     val clientMovieRatingDao: ClientMovieRatingDao,
                                     val clientDao: ClientDao,
                                     val movieDao: MovieDao) : ViewModel() {

    val clientMovieRating = clientMovieRatingDao.getById(id)
    val clientMovieRatingData = MutableLiveData<ClientMovieRatingData>(ClientMovieRatingData())
    val clientMovieRatingWithDetailsDto = clientMovieRatingDao.getByIdWithDetails(id)

    var ratingAsString: String
        get() = clientMovieRatingData.value?.rating?.toString() ?: ""
        set(value) {
            val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
            updatedData.rating = value.toDoubleOrNull() ?: 0.0
            clientMovieRatingData.value = updatedData
        }

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    private val _navigateToClientCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToClientCatalogSelection: LiveData<Boolean> get() = _navigateToClientCatalogSelection

    private val _navigateToMovieCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToMovieCatalogSelection: LiveData<Boolean> get() = _navigateToMovieCatalogSelection

    fun initializationClientMovieRatingData(clientMovieRatingData: ClientMovieRatingData) {
        this.clientMovieRatingData.value = clientMovieRatingData.copy()
    }

    fun updateClientMovieRatingDataFromDto() {
        if (clientMovieRatingData.value?.clientId == null || clientMovieRatingData.value?.movieId == null) {
            clientMovieRatingWithDetailsDto.observeForever { dto ->
                dto?.let {
                    val updatedData = clientMovieRatingData.value?.copy() ?: ClientMovieRatingData()
                    if (updatedData.clientId == null) {
                        updatedData.clientId = it.clientId
                        updatedData.clientFullName = it.clientFullName
                        updatedData.clientDateOfBirth = it.clientDateOfBirth
                        updatedData.clientAddress = it.clientAddress
                        updatedData.clientPhoneNumber = it.clientPhoneNumber
                        updatedData.clientDateOfRegistration = it.clientDateOfRegistration
                        updatedData.clientImageUrl = it.clientImageUrl
                    }
                    if (updatedData.movieId == null) {
                        updatedData.movieId = it.movieId
                        updatedData.movieTitle = it.movieTitle
                        updatedData.movieReleaseYear = it.movieReleaseYear
                        updatedData.movieDirector = it.movieDirector
                        updatedData.movieCountry = it.movieCountry
                        updatedData.movieDuration = it.movieDuration
                        updatedData.movieRentalCost = it.movieRentalCost
                        updatedData.movieAverageRating = it.movieAverageRating
                        updatedData.movieImageUrl = it.movieImageUrl
                    }
                    clientMovieRatingData.value = updatedData
                }
            }
        }
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
        clientLiveDataDataMovie.observeForever { movie ->
            movie?.let {
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

    fun update() {
        clientMovieRating.observeForever { itemToUpdate ->
            viewModelScope.launch {
                if (itemToUpdate != null) {
                    val updatedRating = itemToUpdate.copy(
                        clientId = clientMovieRatingData.value?.clientId
                            ?: clientMovieRatingWithDetailsDto.value?.clientId ?: itemToUpdate.clientId,
                        movieId = clientMovieRatingData.value?.movieId
                            ?: clientMovieRatingWithDetailsDto.value?.movieId ?: itemToUpdate.movieId,
                        rating = clientMovieRatingData.value?.rating
                            ?: clientMovieRatingWithDetailsDto.value?.rating ?: itemToUpdate.rating,
                        comment = clientMovieRatingData.value?.comment
                            ?: clientMovieRatingWithDetailsDto.value?.comment ?: itemToUpdate.comment
                    )
                    clientMovieRatingDao.update(updatedRating)
                    _navigateToViewAfterUpdate.value = true
                }
            }
        }
    }

    fun delete() {
        clientMovieRating.observeForever { itemToUpdate ->
            viewModelScope.launch {
                if (itemToUpdate != null) {
                    clientMovieRatingDao.delete(itemToUpdate)
                    _navigateToViewAfterDelete.value = true
                }
            }
        }
    }

    fun onNavigatedToViewAfterUpdate() {
        _navigateToViewAfterUpdate.value = false
    }

    fun onNavigatedToViewAfterDelete() {
        _navigateToViewAfterDelete.value = false
    }
}