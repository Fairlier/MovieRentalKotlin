package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.util.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchClientViewModel(val dao: ClientDao) : ViewModel() {

    var fullName = ""
    val dateOfBirth = MutableLiveData<String>("")
    var address = ""
    var phoneNumber = ""
    val dateOfRegistration = MutableLiveData<String>("")

    private val _navigateToCatalogAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterSearch: LiveData<Boolean> get() = _navigateToCatalogAfterSearch

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Client.FULL_NAME to fullName.takeIf { it.isNotEmpty() },
            Constants.Client.DATE_OF_BIRTH to dateOfBirth.value.toString().takeIf { it.isNotEmpty() },
            Constants.Client.ADDRESS to address.takeIf { it.isNotEmpty() },
            Constants.Client.PHONE_NUMBER to phoneNumber.takeIf { it.isNotEmpty() },
            Constants.Client.DATE_OF_REGISTRATION to dateOfRegistration.value.toString().takeIf { it.isNotEmpty() }
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
            "dateOfRegistration" -> dateOfRegistration.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}