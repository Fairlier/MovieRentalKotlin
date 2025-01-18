package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.util.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchEmployeeViewModel(val dao: EmployeeDao) : ViewModel() {

    var fullName = ""
    val dateOfBirth  = MutableLiveData<String>("")
    var address = ""
    var phoneNumber = ""
    val dateOfHire = MutableLiveData<String>("")
    val dateOfDismissal = MutableLiveData<String>("")
    var salary = 0.0

    var salaryAsString: String get() = salary.toString()
        set(value) {
            salary = value.toDoubleOrNull() ?: 0.0
        }

    private val _navigateToCatalogAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogAfterSearch

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Employee.FULL_NAME to fullName.takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_BIRTH to dateOfBirth.value.toString().takeIf { it.isNotEmpty() },
            Constants.Employee.ADDRESS to address.takeIf { it.isNotEmpty() },
            Constants.Employee.PHONE_NUMBER to phoneNumber.takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_HIRE to dateOfHire.value.toString().takeIf { it.isNotEmpty() },
            Constants.Employee.DATE_OF_DISMISSAL to dateOfDismissal.value.toString().takeIf { it.isNotEmpty() },
            Constants.Employee.SALARY to if (salary > 0) salary else null
        )
        _navigateToCatalogAfterSearch.value = true
    }

    fun onNavigatedToCatalogAfterSearch() {
        _navigateToCatalogAfterSearch.value = false
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
            "dateOfBirth" -> dateOfBirth.value = formattedDate
            "dateOfHire" -> dateOfHire.value = formattedDate
            "dateOfDismissal" -> dateOfDismissal.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}