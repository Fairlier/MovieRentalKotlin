package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditClientViewModel(id: Long, val dao: ClientDao) : ViewModel() {

    val dateOfBirth = MutableLiveData<String>("")
    val dateOfRegistration = MutableLiveData<String>("")

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    val client = dao.getById(id)

    init {
        client.observeForever { client ->
            if (client != null) {
                dateOfBirth.value = client.dateOfBirth
                dateOfRegistration.value = client.dateOfRegistration
                _currentImageUrl.value = client.imageUrl
            }
        }
    }

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToCatalogAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterDelete: LiveData<Boolean> get() = _navigateToCatalogAfterDelete

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    fun update() {
        viewModelScope.launch {
            val itemToUpdate = client.value
            if (itemToUpdate != null) {
                itemToUpdate.dateOfBirth = dateOfBirth.value.toString()
                itemToUpdate.dateOfRegistration = dateOfRegistration.value.toString()
                itemToUpdate.imageUrl = _currentImageUrl.value
                dao.update(itemToUpdate)
                _navigateToViewAfterUpdate.value = true
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            dao.delete(client.value!!)
            _navigateToCatalogAfterDelete.value = true
        }
    }

    fun onNavigatedToViewAfterUpdate() {
        _navigateToViewAfterUpdate.value = false
    }

    fun onNavigatedToCatalogAfterDelete() {
        _navigateToCatalogAfterDelete.value = false
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