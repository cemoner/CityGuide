package com.example.cityguide.main.util

import androidx.compose.runtime.mutableStateOf

object ProfilePhoneNumberSingleton {

    private val _phoneNumber = mutableStateOf("")

    val getPhoneNumber: String
        get() = _phoneNumber.value


    fun setPHoneNumber(url: String) {
        _phoneNumber.value = url
    }
}
