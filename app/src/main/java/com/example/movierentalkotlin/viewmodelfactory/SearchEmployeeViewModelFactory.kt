package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.viewmodel.SearchEmployeeViewModel

class SearchEmployeeViewModelFactory(private val dao: EmployeeDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchEmployeeViewModel::class.java)) {
            return SearchEmployeeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}