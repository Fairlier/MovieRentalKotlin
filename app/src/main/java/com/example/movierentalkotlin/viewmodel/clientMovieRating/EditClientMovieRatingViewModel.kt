package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.util.ClientMovieRatingData
import kotlinx.coroutines.launch

class EditClientMovieRatingViewModel(val id: Long,
                                     val clientMovieRatingDao: ClientMovieRatingDao,
                                     val clientDao: ClientDao,
                                     val movieDao: MovieDao) : ViewModel() {

    val clientMovieRating = clientMovieRatingDao.getById(id)
    val clientMovieRatingData = MutableLiveData<ClientMovieRatingData>(ClientMovieRatingData())
    val clientMovieRatingWithDetailsDto = clientMovieRatingDao.getByIdWithDetails(id)

    val ratingAsString = MutableLiveData<String>("")

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
        ratingAsString.value = clientMovieRatingData.rating.coerceIn(0.0, 10.0).toString()
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
                    if (updatedData.rating <= 0.0) {
                        updatedData.rating = it.rating
                        ratingAsString.value = it.rating.toString()
                    }
                    if (updatedData.comment.isEmpty()) {
                        updatedData.comment = it.comment
                    }
                    clientMovieRatingData.value = updatedData
                }
            }
        }
    }

    fun onClientCardClicked() {
        clientMovieRatingData.value!!.rating = ratingAsString.value?.toDoubleOrNull() ?: 0.0
        _navigateToClientCatalogSelection.value = true
    }

    fun onClientCardNavigated() {
        _navigateToClientCatalogSelection.value = false
    }

    fun onMovieCardClicked() {
        clientMovieRatingData.value!!.rating = ratingAsString.value?.toDoubleOrNull() ?: 0.0
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
                    val currentData = itemToUpdate.copy(
                        clientId = clientMovieRatingData.value?.clientId
                            ?: clientMovieRatingWithDetailsDto.value?.clientId ?: itemToUpdate.clientId,
                        movieId = clientMovieRatingData.value?.movieId
                            ?: clientMovieRatingWithDetailsDto.value?.movieId ?: itemToUpdate.movieId,
                        rating = (ratingAsString.value?.toDoubleOrNull() ?: itemToUpdate.rating).coerceIn(0.0, 10.0),
                        comment = clientMovieRatingData.value?.comment
                            ?: clientMovieRatingWithDetailsDto.value?.comment ?: itemToUpdate.comment
                    )
                    clientMovieRatingDao.update(currentData)

                    val rawAverageRating = clientMovieRatingDao.calculateAverageRating(
                        clientMovieRatingData.value?.movieId
                            ?: clientMovieRatingWithDetailsDto.value?.movieId ?: itemToUpdate.movieId
                    )

                    if (rawAverageRating != null) {
                        val normalizedAverageRating = rawAverageRating.coerceIn(0.0, 10.0)
                        movieDao.updateAverageRating(
                            clientMovieRatingData.value?.movieId
                                ?: clientMovieRatingWithDetailsDto.value?.movieId ?: itemToUpdate.movieId,
                            normalizedAverageRating
                        )
                    }

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