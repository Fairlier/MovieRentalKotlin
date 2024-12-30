package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.entity.Employee
import kotlinx.coroutines.launch

class InsertEmployeeViewModel(val dao: EmployeeDao) : ViewModel() {

    var fullName = ""
    var dateOfBirth = ""
    var address = ""
    var phoneNumber = ""
    var dateOfHire = ""
    var dateOfDismissal = ""
    var salary = 0.0
    var imageUrl: String? = null

    var salaryAsString: String get() = salary.toString()
        set(value) {
            salary = value.toDoubleOrNull() ?: 0.0
        }

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
//                phoneNumber.isBlank() || dateOfHire.isBlank() ||
//                dateOfDismissal.isBlank() || salary <= 0.0
            ) {
                _showValidationError.value = true
                return@launch
            }
            val employee = Employee(
                fullName = fullName,
                dateOfBirth = dateOfBirth,
                address = address,
                phoneNumber = phoneNumber,
                dateOfHire = dateOfHire,
                dateOfDismissal = dateOfDismissal,
                salary = salary,
                imageUrl = _currentImageUrl.value
            )
            dao.insert(employee)
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