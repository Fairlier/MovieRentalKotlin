package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.util.Constants

class ClientCatalogViewModel(val dao: ClientDao) : ViewModel() {

    private val _navigateToView = MutableLiveData<Long?>()
    val navigateToView: LiveData<Long?> get() = _navigateToView

    private val _navigateToInsert = MutableLiveData<Boolean>(false)
    val navigateToInsert: LiveData<Boolean> get() = _navigateToInsert

    private val _catalog = MutableLiveData<List<Client>>()
    val catalog: LiveData<List<Client>> get() = _catalog

    fun onCatalogItemClicked(id: Long) {
        _navigateToView.value = id
    }

    fun onCatalogItemNavigated() {
        _navigateToView.value = null
    }

    fun insert() {
        _navigateToInsert.value = true
    }

    fun onNavigatedToInsert() {
        _navigateToInsert.value = false
    }

    init {
        search(emptyMap())
    }

    fun setFilters(filters: Map<String, Any?>) {
        search(filters)
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