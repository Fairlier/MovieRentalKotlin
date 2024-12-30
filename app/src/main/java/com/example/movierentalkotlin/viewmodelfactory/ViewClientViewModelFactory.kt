package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.viewmodel.ViewClientViewModel

class ViewClientViewModelFactory(private val id: Long, private val dao: ClientDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewClientViewModel::class.java)) {
            return ViewClientViewModel(id, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}