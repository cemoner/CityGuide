package com.example.cityguide.main.util

class IsLoggedInSingleton private constructor() {
    companion object {
        private var isLoggedIn: Boolean = false

        fun getIsLoggedIn(): Boolean = isLoggedIn

        fun setIsLoggedIn(boolean: Boolean) {
            isLoggedIn = boolean
        }
    }
}
