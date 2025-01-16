package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.entity.Client
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InsertClientViewModel(val dao: ClientDao) : ViewModel() {

    var fullName = ""
    val dateOfBirth = MutableLiveData<String>("")
    var address = ""
    var phoneNumber = ""
    val dateOfRegistration = MutableLiveData<String>("")
    var imageUrl: String? = null

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
//                phoneNumber.isBlank() || dateOfRegistration.isBlank()
            ) {
                _showValidationError.value = true
                return@launch
            }
            val client = Client(
                fullName = fullName,
                dateOfBirth = dateOfBirth.value.toString(),
                address = address,
                phoneNumber = phoneNumber,
                dateOfRegistration = dateOfRegistration.value.toString(),
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
            "dateOfRegistration" -> dateOfRegistration.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}