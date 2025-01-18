package com.example.movierentalkotlin.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.entity.Employee
import com.example.movierentalkotlin.util.Constants

class EmployeeCatalogViewModel(val dao: EmployeeDao) : ViewModel() {

    private val _catalog = MutableLiveData<List<Employee>>()
    val catalog: LiveData<List<Employee>> get() = _catalog

    private val _navigateToView = MutableLiveData<Long?>()
    val navigateToView: LiveData<Long?> get() = _navigateToView

    private val _navigateToInsert = MutableLiveData<Boolean>(false)
    val navigateToInsert: LiveData<Boolean> get() = _navigateToInsert

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
                filters[Constants.Employee.FULL_NAME] as? String,
                filters[Constants.Employee.DATE_OF_BIRTH] as? String,
                filters[Constants.Employee.ADDRESS] as? String,
                filters[Constants.Employee.PHONE_NUMBER] as? String,
                filters[Constants.Employee.DATE_OF_HIRE] as? String,
                filters[Constants.Employee.DATE_OF_DISMISSAL] as? String,
                filters[Constants.Employee.SALARY] as? Double
            )
        }
        itemsLiveData.observeForever { items ->
            _catalog.postValue(items)
        }
    }
}