package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.util.Constants

class ClientCatalogSelectionViewModel(val dao: ClientDao) : ViewModel() {

    private val _catalog = MutableLiveData<List<Client>>()
    val catalog: LiveData<List<Client>> get() = _catalog

    private val _navigateToBack = MutableLiveData<Long?>()
    val navigateToBack: LiveData<Long?> get() = _navigateToBack

    fun onCatalogItemClicked(id: Long) {
        _navigateToBack.value = id
    }

    fun onCatalogItemNavigated() {
        _navigateToBack.value = null
    }

    fun setFilters(filters: Map<String, Any?>) {
        search(filters)
    }

    init {
        search(emptyMap())
    }

    private fun search(filters: Map<String, Any?>) {
        val itemsLiveData = if (filters.isEmpty()) {
            dao.getAll()
        } else {
            dao.search(
                filters[Constants.Client.FULL_NAME] as? String,
                filters[Constants.Client.DATE_OF_BIRTH] as? String,
                filters[Constants.Client.ADDRESS] as? String,
                filters[Constants.Client.PHONE_NUMBER] as? String,
                filters[Constants.Client.DATE_OF_REGISTRATION] as? String
            )
        }
        itemsLiveData.observeForever { items ->
            _catalog.postValue(items)
        }
    }
}