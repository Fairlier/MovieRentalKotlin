package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.EmployeeDao

class ViewEmployeeViewModel(id: Long, val dao: EmployeeDao) : ViewModel() {

    private val _salaryAsString = MutableLiveData<String>()
    val salaryAsString: LiveData<String> = _salaryAsString

    val employee = dao.getById(id)

    init {
        employee.observeForever { employee ->
            if (employee != null) {
                _salaryAsString.value = employee.salary.toString()
            }
        }
    }
}