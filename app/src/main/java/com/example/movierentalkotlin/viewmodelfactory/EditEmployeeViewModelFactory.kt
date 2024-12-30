package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.viewmodel.EditEmployeeViewModel

class EditEmployeeViewModelFactory(private val id: Long, private val dao: EmployeeDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditEmployeeViewModel::class.java)) {
            return EditEmployeeViewModel(id, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}