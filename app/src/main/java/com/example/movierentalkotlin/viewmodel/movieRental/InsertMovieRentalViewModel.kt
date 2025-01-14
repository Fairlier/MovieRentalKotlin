package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.entity.MovieRental
import com.example.movierentalkotlin.util.MovieRentalData
import kotlinx.coroutines.launch

class InsertMovieRentalViewModel(val movieRentalDao: MovieRentalDao,
                                 val clientDao: ClientDao,
                                 val employeeDao: EmployeeDao,
                                 val movieDao: MovieDao) : ViewModel() {

    val movieRentalData = MutableLiveData<MovieRentalData>(MovieRentalData())

    private val _navigateToCatalogAfterInsert = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterInsert: LiveData<Boolean> get() = _navigateToCatalogAfterInsert

    private val _navigateToClientCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToClientCatalogSelection: LiveData<Boolean> get() = _navigateToClientCatalogSelection

    private val _navigateToEmployeeCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToEmployeeCatalogSelection: LiveData<Boolean> get() = _navigateToEmployeeCatalogSelection

    private val _navigateToMovieCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToMovieCatalogSelection: LiveData<Boolean> get() = _navigateToMovieCatalogSelection

    private val _showValidationError = MutableLiveData<Boolean>(false)
    val showValidationError: LiveData<Boolean> get() = _showValidationError

    fun initializationMovieRentalData(movieRentalData: MovieRentalData) {
        this.movieRentalData.value = movieRentalData.copy()
    }

    fun onClientCardClicked() {
        _navigateToClientCatalogSelection.value = true
    }

    fun onClientCardNavigated() {
        _navigateToClientCatalogSelection.value = false
    }

    fun onEmployeeCardClicked() {
        _navigateToEmployeeCatalogSelection.value = true
    }

    fun onEmployeeCardNavigated() {
        _navigateToEmployeeCatalogSelection.value = false
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
                val updatedData = movieRentalData.value?.copy() ?: MovieRentalData()
                updatedData.clientId = it.id
                updatedData.clientFullName = it.fullName
                updatedData.clientDateOfBirth = it.dateOfBirth
                updatedData.clientAddress = it.address
                updatedData.clientPhoneNumber = it.phoneNumber
                updatedData.clientDateOfRegistration = it.dateOfRegistration
                updatedData.clientImageUrl = it.imageUrl
                movieRentalData.postValue(updatedData)
            }
        }
    }

    fun updateEmployee(id: Long) {
        val employeeLiveData = employeeDao.getById(id)
        employeeLiveData.observeForever { employee ->
            employee?.let {
                val updatedData = movieRentalData.value?.copy() ?: MovieRentalData()
                updatedData.employeeId = it.id
                updatedData.employeeFullName = it.fullName
                updatedData.employeeDateOfBirth = it.dateOfBirth
                updatedData.employeeAddress = it.address
                updatedData.employeePhoneNumber = it.phoneNumber
                updatedData.employeeDateOfHire = it.dateOfHire
                updatedData.employeeDateOfDismissal = it.dateOfDismissal
                updatedData.employeeSalary = it.salary
                updatedData.clientImageUrl = it.imageUrl
                movieRentalData.postValue(updatedData)
            }
        }
    }

    fun updateMovie(id: Long) {
        val movieLiveData = movieDao.getById(id)
        movieLiveData.observeForever { movie ->
            movie?.let {
                val updatedData = movieRentalData.value?.copy() ?: MovieRentalData()
                updatedData.movieId = it.id
                updatedData.movieTitle = it.title
                updatedData.movieReleaseYear = it.releaseYear
                updatedData.movieDirector = it.director
                updatedData.movieCountry = it.country
                updatedData.movieDuration = it.duration
                updatedData.movieRentalCost = it.rentalCost
                updatedData.movieAverageRating = it.averageRating
                updatedData.movieImageUrl = it.imageUrl
                movieRentalData.postValue(updatedData)
            }
        }
    }

    fun insert() {
        viewModelScope.launch {
            val currentData = movieRentalData.value?.copy() ?: MovieRentalData()
            if (currentData.clientId == null || currentData.employeeId == null || currentData.movieId == null) {
                _showValidationError.value = true
                return@launch
            }

            val movieRental = MovieRental(
                clientId = currentData.clientId!!,
                employeeId = currentData.employeeId!!,
                movieId = currentData.movieId!!,
                dateOfReceipt = currentData.dateOfReceipt,
                dateOfReturn = currentData.dateOfReturn
            )
            movieRentalDao.insert(movieRental)
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