package com.example.movierentalkotlin.viewmodelfactory.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.viewmodel.employee.ViewEmployeeViewModel

class ViewEmployeeViewModelFactory(private val id: Long, private val dao: EmployeeDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewEmployeeViewModel::class.java)) {
            return ViewEmployeeViewModel(id, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}