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

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private val _selectedRole = MutableLiveData<String>()
    val selectedRole: LiveData<String> get() = _selectedRole

    fun login(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", true)
            putString("userRole", _selectedRole.value)
            apply()
        }
        _isLoggedIn.value = true
        _navigateToCatalog.value = true
    }

    fun logout(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            putString("userRole", null)
            apply()
        }
        _isLoggedIn.value = false
        _navigateToCatalog.value = false
    }

    fun onNavigatedToCatalog() {
        _navigateToCatalog.value = false
    }

    fun selectRole(role: String) {
        _selectedRole.value = role
    }
}