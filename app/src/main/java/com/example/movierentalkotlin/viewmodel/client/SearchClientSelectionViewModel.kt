package com.example.movierentalkotlin.viewmodel.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.util.Constants

class SearchClientSelectionViewModel(val dao: ClientDao) : ViewModel() {

    var fullName = ""
    var dateOfBirth = ""
    var address = ""
    var phoneNumber = ""
    var dateOfRegistration = ""

    private val _navigateToViewAfterSearch = MutableLiveData<Boolean>(false)
    val navigateToViewAfterSearch: LiveData<Boolean> get() = _navigateToViewAfterSearch

    private val _filters = MutableLiveData<Map<String, Any?>>()
    val filters: LiveData<Map<String, Any?>> get() = _filters

    fun search() {
        _filters.value = mapOf(
            Constants.Client.FULL_NAME to fullName.takeIf { it.isNotEmpty() },
            Constants.Client.DATE_OF_BIRTH to dateOfBirth.takeIf { it.isNotEmpty() },
            Constants.Client.ADDRESS to address.takeIf { it.isNotEmpty() },
            Constants.Client.PHONE_NUMBER to phoneNumber.takeIf { it.isNotEmpty() },
            Constants.Client.DATE_OF_REGISTRATION to dateOfRegistration.takeIf { it.isNotEmpty() },
        )
        _navigateToViewAfterSearch.value = true
    }

    fun onNavigatedToViewAfterSearch() {
        _navigateToViewAfterSearch.value = false
    }
}