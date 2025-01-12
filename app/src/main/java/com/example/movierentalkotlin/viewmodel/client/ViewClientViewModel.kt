package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao

class ViewClientViewModel(id: Long, val dao: ClientDao) : ViewModel() {

    val client = dao.getById(id)
}