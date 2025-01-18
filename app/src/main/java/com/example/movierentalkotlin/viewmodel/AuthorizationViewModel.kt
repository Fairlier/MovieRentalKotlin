package com.example.movierentalkotlin.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.util.Constants

class AuthorizationViewModel : ViewModel() {

    private val _navigateToCatalog = MutableLiveData<Boolean>(false)
    val navigateToCatalog: LiveData<Boolean> get() = _navigateToCatalog

    private val _selectedRole = MutableLiveData<String>()
    val selectedRole: LiveData<String> get() = _selectedRole

    fun login() {
        _navigateToCatalog.value = true
    }

    fun onNavigatedToCatalog() {
        _navigateToCatalog.value = false
    }

    fun selectRole(role: String) {
        _selectedRole.value = role
    }
}