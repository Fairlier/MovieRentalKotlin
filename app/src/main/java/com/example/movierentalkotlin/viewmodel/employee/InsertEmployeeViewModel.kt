package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.entity.Employee
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InsertEmployeeViewModel(val dao: EmployeeDao) : ViewModel() {

    var fullName = ""
    val dateOfBirth  = MutableLiveData<String>("")
    var address = ""
    var phoneNumber = ""
    val dateOfHire = MutableLiveData<String>("")
    val dateOfDismissal = MutableLiveData<String>("")
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

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

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
                dateOfBirth = dateOfBirth.value.toString(),
                address = address,
                phoneNumber = phoneNumber,
                dateOfHire = dateOfHire.value.toString(),
                dateOfDismissal = dateOfDismissal.value.toString(),
                salary = salary,
                imageUrl = _currentImageUrl.value.toString()
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