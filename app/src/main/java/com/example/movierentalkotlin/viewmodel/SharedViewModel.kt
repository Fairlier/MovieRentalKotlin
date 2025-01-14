package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.util.ClientMovieRatingData
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.util.MovieRentalData

class SharedViewModel : ViewModel() {

    private val _movieFilters = MutableLiveData<Map<String, Any?>>()
    val movieFilters: LiveData<Map<String, Any?>> get() = _movieFilters

    private val _clientFilters = MutableLiveData<Map<String, Any?>>()
    val clientFilters: LiveData<Map<String, Any?>> get() = _clientFilters

    private val _employeeFilters = MutableLiveData<Map<String, Any?>>()
    val employeeFilters: LiveData<Map<String, Any?>> get() = _employeeFilters

    private val _movieRentalFilters = MutableLiveData<Map<String, Any?>>()
    val movieRentalFilters: LiveData<Map<String, Any?>> get() = _movieRentalFilters

    private val _clientMovieRatingFilters = MutableLiveData<Map<String, Any?>>()
    val clientMovieRatingFilters: LiveData<Map<String, Any?>> get() = _clientMovieRatingFilters

    private val _sourceFragment = MutableLiveData<Constants.FragmentSource?>()
    val sourceFragment: LiveData<Constants.FragmentSource?> get() = _sourceFragment

    private val _selectedClientId = MutableLiveData<Long?>()
    val selectedClientId: LiveData<Long?> get() = _selectedClientId

    private val _selectedMovieId = MutableLiveData<Long?>()
    val selectedMovieId: LiveData<Long?> get() = _selectedMovieId

    private val _selectedEmployeeId = MutableLiveData<Long?>()
    val selectedEmployeeId: LiveData<Long?> get() = _selectedEmployeeId

    var clientMovieRatingData = ClientMovieRatingData()

    var movieRentalData = MovieRentalData()

    var currentIdForEditClientMovieRating: Long? = null

    var currentIdForEditMovieRental: Long? = null

    fun setMovieFilters(filters: Map<String, Any?>) {
        _movieFilters.value = filters
    }

    fun setClientFilters(filters: Map<String, Any?>) {
        _clientFilters.value = filters
    }

    fun setEmployeeFilters(filters: Map<String, Any?>) {
        _employeeFilters.value = filters
    }

    fun setMovieRentalFilters(filters: Map<String, Any?>) {
        _movieRentalFilters.value = filters
    }

    fun setClientMovieRatingFilters(filters: Map<String, Any?>) {
        _clientMovieRatingFilters.value = filters
    }

    fun setSourceFragment(source: Constants.FragmentSource) {
        _sourceFragment.value = source
    }

    fun clearSourceFragment() {
        _sourceFragment.value = null
    }

    fun setSelectedClientId(id: Long?) {
        _selectedClientId.value = id
    }

    fun clearSelectedClientId() {
        _selectedClientId.value = null
    }

    fun setSelectedMovieId(id: Long?) {
        _selectedMovieId.value = id
    }

    fun clearSelectedMovieId() {
        _selectedMovieId.value = null
    }

    fun setSelectedEmployeeId(id: Long?) {
        _selectedEmployeeId.value = id
    }

    fun clearSelectedEmployeeId() {
        _selectedEmployeeId.value = null
    }

    fun initializationClientMovieRatingData(clientMovieRatingData: ClientMovieRatingData) {
        this.clientMovieRatingData = clientMovieRatingData.copy()
    }

    fun clearClientMovieRatingData() {
        this.clientMovieRatingData = ClientMovieRatingData()
    }

    fun initializationMovieRentalData(movieRentalData: MovieRentalData) {
        this.movieRentalData = movieRentalData.copy()
    }

    fun clearMovieRentalData() {
        this.movieRentalData = MovieRentalData()
    }

    fun clearCurrentIdForEditClientMovieRating() {
        this.currentIdForEditClientMovieRating = null
    }

    fun clearCurrentIdForEditMovieRental() {
        this.currentIdForEditMovieRental = null
    }
}