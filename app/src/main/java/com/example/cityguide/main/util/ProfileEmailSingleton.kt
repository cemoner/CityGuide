package com.example.cityguide.main.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ProfileEmailSingleton {
    private val _profileEmail = MutableStateFlow("")

    val profileEmail: StateFlow<String> get() = _profileEmail

    fun setEmail(email: String) {
        _profileEmail.value = email
    }
}