package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.util.MovieRentalData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchMovieRentalViewModel(val movieRentalDao: MovieRentalDao,
                                 val clientDao: ClientDao,
                                 val employeeDao: EmployeeDao,
                                 val movieDao: MovieDao) : ViewModel() {

    val movieRentalData = MutableLiveData<MovieRentalData>(MovieRentalData())

    val dateOfReceipt = MutableLiveData<String>("")
    val dateOfReturn = MutableLiveData<String>("")

    private val _navigateToCatalogAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogAfterSearch

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    private val _navigateToClientCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToClientCatalogSelection: LiveData<Boolean> get() = _navigateToClientCatalogSelection

    private val _navigateToEmployeeCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToEmployeeCatalogSelection: LiveData<Boolean> get() = _navigateToEmployeeCatalogSelection

    private val _navigateToMovieCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToMovieCatalogSelection: LiveData<Boolean> get() = _navigateToMovieCatalogSelection

    private val _showValidationError = MutableLiveData<Boolean>(false)
    val showValidationError: LiveData<Boolean> get() = _showValidationError

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    fun initializationMovieRentalData(movieRentalData: MovieRentalData) {
        this.movieRentalData.value = movieRentalData.copy()
        dateOfReceipt.value = movieRentalData.dateOfReceipt
        dateOfReturn.value = movieRentalData.dateOfReturn
    }

    fun onClientCardClicked() {
        movieRentalData.value!!.dateOfReceipt = dateOfReceipt.value.toString()
        movieRentalData.value!!.dateOfReturn = dateOfReturn.value.toString()
        _navigateToClientCatalogSelection.value = true
    }

    fun onClientCardNavigated() {
        _navigateToClientCatalogSelection.value = false
    }

    fun onEmployeeCardClicked() {
        movieRentalData.value!!.dateOfReceipt = dateOfReceipt.value.toString()
        movieRentalData.value!!.dateOfReturn = dateOfReturn.value.toString()
        _navigateToEmployeeCatalogSelection.value = true
    }

    fun onEmployeeCardNavigated() {
        _navigateToEmployeeCatalogSelection.value = false
    }

    fun onMovieCardClicked() {
        movieRentalData.value!!.dateOfReceipt = dateOfReceipt.value.toString()
        movieRentalData.value!!.dateOfReturn = dateOfReturn.value.toString()
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

    fun search() {
        val currentData = movieRentalData.value?.copy() ?: MovieRentalData()
        _filters.value = mapOf(
            Constants.MovieRental.CLIENT_ID to  currentData.clientId,
            Constants.MovieRental.EMPLOYEE_ID to  currentData.employeeId,
            Constants.MovieRental.MOVIE_ID to currentData.movieId,
            Constants.MovieRental.DATE_OF_RECEIPT to dateOfReceipt.value.toString().takeIf { it.isNotEmpty() },
            Constants.MovieRental.DATE_OF_RETURN to dateOfReturn.value.toString().takeIf { it.isNotEmpty() }
        )
        _navigateToCatalogAfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogAfterSearch.value = false
    }

    fun onValidationErrorShown() {
        _showValidationError.value = false
    }

    fun showDatePicker(field: String) {
        _showDatePickerForField.value = field
    }

    fun onDateSelected(year: Int, month: Int, day: Int, field: String) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)

        when (field) {
            "dateOfReceipt" -> dateOfReceipt.value = formattedDate
            "dateOfReturn" -> dateOfReturn.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}