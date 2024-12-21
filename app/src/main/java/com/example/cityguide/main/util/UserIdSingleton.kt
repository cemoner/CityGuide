package com.example.cityguide.main.util

class UserIdSingleton private constructor() {
    companion object {
        private var userId: String = "-1"

        fun getUserId(): String = userId

        fun setUserId(name: String) {
            userId = name
        }
    }
}
