package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.EmployeeDao
import kotlinx.coroutines.launch

class EditEmployeeViewModel(id: Long, val dao: EmployeeDao) : ViewModel() {

    val _salaryAsString = MutableLiveData<String>()

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToViewAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToViewAfterDelete: LiveData<Boolean> get() = _navigateToViewAfterDelete

    val employee = dao.getById(id)

    init {
        employee.observeForever { employee ->
            if (employee != null) {
                _salaryAsString.value = employee.salary.toString()
                _currentImageUrl.value = employee.imageUrl
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            val itemToUpdate = employee.value
            if (itemToUpdate != null) {
                itemToUpdate.salary = _salaryAsString.value?.toDoubleOrNull() ?: 0.0
                itemToUpdate.imageUrl = _currentImageUrl.value
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
}