package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.EmployeeDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditEmployeeViewModel(id: Long, val dao: EmployeeDao) : ViewModel() {

    val dateOfBirth  = MutableLiveData<String>("")
    val dateOfHire = MutableLiveData<String>("")
    val dateOfDismissal = MutableLiveData<String>("")

    val _salaryAsString = MutableLiveData<String>()

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    val employee = dao.getById(id)

    init {
        employee.observeForever { employee ->
            if (employee != null) {
                dateOfBirth.value = employee.dateOfBirth
                dateOfHire.value = employee.dateOfHire
                dateOfDismissal.value = employee.dateOfDismissal
                _salaryAsString.value = employee.salary.toString()
                _currentImageUrl.value = employee.imageUrl
            }
        }
    }

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    private val _showDatePickerForField = MutableLiveData<String?>()
    val showDatePickerForField: LiveData<String?> get() = _showDatePickerForField

    fun update() {
        viewModelScope.launch {
            val itemToUpdate = employee.value
            if (itemToUpdate != null) {
                itemToUpdate.dateOfBirth = dateOfBirth.value.toString()
                itemToUpdate.dateOfHire = dateOfHire.value.toString()
                itemToUpdate.dateOfDismissal = dateOfDismissal.value.toString()
                itemToUpdate.salary = _salaryAsString.value?.toDoubleOrNull() ?: 0.0
                itemToUpdate.imageUrl = _currentImageUrl.value.toString()
                dao.update(itemToUpdate)
                _navigateToViewAfterUpdate.value = true
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            dao.delete(employee.value!!)
            _navigateToViewAfterDelete.value = true
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
            "dateOfBirth" -> dateOfBirth.value = formattedDate
            "dateOfHire" -> dateOfHire.value = formattedDate
            "dateOfDismissal" -> dateOfDismissal.value = formattedDate
        }
    }

    fun onDatePickerShown() {
        _showDatePickerForField.value = null
    }
}