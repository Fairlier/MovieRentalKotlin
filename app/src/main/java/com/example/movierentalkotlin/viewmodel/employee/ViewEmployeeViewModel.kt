package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.EmployeeDao

class ViewEmployeeViewModel(id: Long, val dao: EmployeeDao) : ViewModel() {

    val employee = dao.getById(id)
}