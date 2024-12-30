package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.viewmodel.EmployeeCatalogViewModel

class EmployeeCatalogViewModelFactory(private val dao: EmployeeDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeCatalogViewModel::class.java)) {
            return EmployeeCatalogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}