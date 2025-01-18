package com.example.movierentalkotlin.util

import android.content.Context

class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ROLE = "userRole"
    }

    fun setLoginState(isLoggedIn: Boolean, role: String? = null) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        role?.let { editor.putString(KEY_USER_ROLE, it) }
        editor.apply()
    }

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getUserRole(): String? = sharedPreferences.getString(KEY_USER_ROLE, null)

    fun logout() {
        editor.clear()
        editor.apply()
    }
}