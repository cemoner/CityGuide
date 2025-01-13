package com.example.cityguide.feature.auth.domain.repository

interface UserInfoRepository {
    suspend fun getProfileUrl(): String

    suspend fun getProfileName(): String

    fun isUserLoggedIn():Boolean
}