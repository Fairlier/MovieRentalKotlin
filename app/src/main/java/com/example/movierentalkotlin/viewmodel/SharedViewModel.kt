package com.example.movierentalkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _movieFilters = MutableLiveData<Map<String, Any?>>()
    val movieFilters: LiveData<Map<String, Any?>> get() = _movieFilters

    fun setMovieFilters(filters: Map<String, Any?>) {
        _movieFilters.value = filters
    }

}