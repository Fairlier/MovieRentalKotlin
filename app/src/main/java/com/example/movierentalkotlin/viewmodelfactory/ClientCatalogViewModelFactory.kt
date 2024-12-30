package com.example.movierentalkotlin.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.viewmodel.ClientCatalogViewModel

class ClientCatalogViewModelFactory(private val dao: ClientDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientCatalogViewModel::class.java)) {
            return ClientCatalogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}