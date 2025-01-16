package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.util.MovieRentalData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditMovieRentalViewModel(val id: Long,
                               val movieRentalDao: MovieRentalDao,
                               val clientDao: ClientDao,
                               val employeeDao: EmployeeDao,
                               val movieDao: MovieDao) : ViewModel() {

    val movieRental = movieRentalDao.getById(id)
    val movieRentalData = MutableLiveData<MovieRentalData>(MovieRentalData())
    val movieRentalWithDetailsDto = movieRentalDao.getByIdWithDetails(id)

    val dateOfReceipt = MutableLiveData<String>("")
    val dateOfReturn = MutableLiveData<String>("")

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    private val _navigateToClientCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToClientCatalogSelection: LiveData<Boolean> get() = _navigateToClientCatalogSelection

    private val _navigateToEmployeeCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToEmployeeCatalogSelection: LiveData<Boolean> get() = _navigateToEmployeeCatalogSelection

    private val _navigateToMovieCatalogSelection = MutableLiveData<Boolean>(false)
    val navigateToMovieCatalogSelection: LiveData<Boolean> get() = _navigateToMovieCatalogSelection

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    private fun updateMovieRentalData(update: (MovieRentalData) -> MovieRentalData) {
        val currentData = movieRentalData.value ?: MovieRentalData()
        movieRentalData.value = update(currentData)
    }

    fun initializationMovieRentalData(movieRentalData: MovieRentalData) {
        this.movieRentalData.value = movieRentalData.copy()
    }

    fun updateMovieRentalDataFromDto() {
        if (movieRentalData.value?.clientId == null || movieRentalData.value?.employeeId == null || movieRentalData.value?.movieId == null) {
            movieRentalWithDetailsDto.observeForever { dto ->
                dto?.let {
                    val updatedData = movieRentalData.value?.copy() ?: MovieRentalData()
                    if (updatedData.clientId == null) {
                        updatedData.clientId = it.clientId
                        updatedData.clientFullName = it.clientFullName
                        updatedData.clientDateOfBirth = it.clientDateOfBirth
                        updatedData.clientAddress = it.clientAddress
                        updatedData.clientPhoneNumber = it.clientPhoneNumber
                        updatedData.clientDateOfRegistration = it.clientDateOfRegistration
                        updatedData.clientImageUrl = it.clientImageUrl
                    }
                    if (updatedData.employeeId == null) {
                        updatedData.employeeId = it.employeeId
                        updatedData.employeeFullName = it.employeeFullName
                        updatedData.employeeDateOfBirth = it.employeeDateOfBirth
                        updatedData.employeeAddress = it.employeeAddress
                        updatedData.employeePhoneNumber = it.employeePhoneNumber
                        updatedData.employeeDateOfHire = it.employeeDateOfHire
                        updatedData.employeeDateOfDismissal = it.employeeDateOfDismissal
                        updatedData.employeeSalary = it.employeeSalary
                        updatedData.employeeImageUrl = it.employeeImageUrl
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
                    if (updatedData.dateOfReceipt.isEmpty()) {
                        updatedData.dateOfReceipt = it.dateOfReceipt
                        dateOfReceipt.value = it.dateOfReceipt
                    }
                    if (updatedData.dateOfReturn.isEmpty()) {
                        updatedData.dateOfReturn = it.dateOfReturn
                        dateOfReturn.value = it.dateOfReturn
                    }
                    movieRentalData.value = updatedData
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

                updatedData.dateOfReceipt = dateOfReceipt.value.toString()
                updatedData.dateOfReturn = dateOfReturn.value.toString()
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

                updatedData.dateOfReceipt = dateOfReceipt.value.toString()
                updatedData.dateOfReturn = dateOfReturn.value.toString()
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

                updatedData.dateOfReceipt = dateOfReceipt.value.toString()
                updatedData.dateOfReturn = dateOfReturn.value.toString()
                movieRentalData.postValue(updatedData)
            }
        }
    }

    fun update() {
        movieRental.observeForever { itemToUpdate ->
            viewModelScope.launch {
                if (itemToUpdate != null) {
                    val updatedRating = itemToUpdate.copy(
                        clientId = movieRentalData.value?.clientId
                            ?: movieRentalWithDetailsDto.value?.clientId ?: itemToUpdate.clientId,
                        employeeId = movieRentalData.value?.employeeId
                            ?: movieRentalWithDetailsDto.value?.employeeId ?: itemToUpdate.employeeId,
                        movieId = movieRentalData.value?.movieId
                            ?: movieRentalWithDetailsDto.value?.movieId ?: itemToUpdate.movieId,
                        dateOfReceipt = dateOfReceipt.value.toString(),
                        dateOfReturn = dateOfReturn.value.toString()
                    )
                    movieRentalDao.update(updatedRating)
                    _navigateToViewAfterUpdate.value = true
                }
            }
        }
    }

    fun delete() {
        movieRental.observeForever { itemToUpdate ->
            viewModelScope.launch {
                if (itemToUpdate != null) {
                    movieRentalDao.delete(itemToUpdate)
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