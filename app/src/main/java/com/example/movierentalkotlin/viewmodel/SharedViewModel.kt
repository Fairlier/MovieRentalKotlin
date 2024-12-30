package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}