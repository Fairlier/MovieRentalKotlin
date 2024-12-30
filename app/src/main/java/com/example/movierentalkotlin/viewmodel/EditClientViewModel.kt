package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierentalkotlin.database.dao.ClientDao
import kotlinx.coroutines.launch

class EditClientViewModel(id: Long, val dao: ClientDao) : ViewModel() {

    val _currentImageUrl = MutableLiveData<String?>()
    val currentImageUrl: LiveData<String?> get() = _currentImageUrl

    private val _navigateToViewAfterUpdate = MutableLiveData<Boolean>(false)
    val navigateToViewAfterUpdate: LiveData<Boolean> get() = _navigateToViewAfterUpdate

    private val _navigateToCatalogAfterDelete = MutableLiveData<Boolean>(false)
    val navigateToCatalogAfterDelete: LiveData<Boolean> get() = _navigateToCatalogAfterDelete

    val client = dao.getById(id)

    init {
        client.observeForever { client ->
            if (client != null) {
                _currentImageUrl.value = client.imageUrl
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            val itemToUpdate = client.value
            if (itemToUpdate != null) {
                itemToUpdate.imageUrl = _currentImageUrl.value
                dao.update(itemToUpdate)
                _navigateToViewAfterUpdate.value = true
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            dao.delete(client.value!!)
            _navigateToCatalogAfterDelete.value = true
        }
    }

    fun onNavigatedToViewAfterUpdate() {
        _navigateToViewAfterUpdate.value = false
    }

    fun onNavigatedToCatalogAfterDelete() {
        _navigateToCatalogAfterDelete.value = false
    }
}