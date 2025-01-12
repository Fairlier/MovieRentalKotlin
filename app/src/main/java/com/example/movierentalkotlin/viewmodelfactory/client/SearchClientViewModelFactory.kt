package com.example.movierentalkotlin.viewmodelfactory.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.viewmodel.client.SearchClientViewModel

class SearchClientViewModelFactory(private val dao: ClientDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchClientViewModel::class.java)) {
            return SearchClientViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}