package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.entity.Client
import kotlinx.coroutines.launch

class InsertClientViewModel(val dao: ClientDao) : ViewModel() {

    var fullName = ""
    var dateOfBirth = ""
    var address = ""
    var phoneNumber = ""
    var dateOfRegistration = ""
    var imageUrl: String? = null

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    private val _navigateToCatalogAfterInsert = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterInsert: LiveData<Boolean> get() = _navigateToCatalogAfterInsert

    private val _showValidationError = MutableLiveData<Boolean>(false)
    val showValidationError: LiveData<Boolean> get() = _showValidationError

    fun insert() {
        viewModelScope.launch {
            if (fullName.isBlank()
//                || dateOfBirth.isBlank() || address.isBlank() ||
//                phoneNumber.isBlank() || dateOfRegistration.isBlank()
            ) {
                _showValidationError.value = true
                return@launch
            }
            val client = Client(
                fullName = fullName,
                dateOfBirth = dateOfBirth,
                address = address,
                phoneNumber = phoneNumber,
                dateOfRegistration = dateOfRegistration,
                imageUrl = _currentImageUrl.value
            )
            dao.insert(client)
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