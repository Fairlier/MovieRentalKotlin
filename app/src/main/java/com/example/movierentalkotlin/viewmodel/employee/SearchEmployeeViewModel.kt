package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.util.Constants

class SearchEmployeeViewModel(val dao: EmployeeDao) : ViewModel() {

    var fullName = ""
    var dateOfBirth = ""
    var address = ""
    var phoneNumber = ""
    var dateOfHire = ""
    var dateOfDismissal = ""
    var salary = 0.0

    var salaryAsString: String get() = salary.toString()
        set(value) {
            salary = value.toDoubleOrNull() ?: 0.0
        }

    private val _navigateToCatalogfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogfterSearch

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Employee.FULL_NAME to fullName.takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_BIRTH to dateOfBirth.takeIf { it.isNotEmpty() },
            Constants.Employee.ADDRESS to address.takeIf { it.isNotEmpty() },
            Constants.Employee.PHONE_NUMBER to phoneNumber.takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_HIRE to dateOfHire.takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_DISMISSAL to dateOfDismissal.takeIf { it.isNotEmpty() },
            Constants.Employee.SALARY to if (salary > 0) salary else null
        )
        _navigateToCatalogfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogfterSearch.value = false
    }
}