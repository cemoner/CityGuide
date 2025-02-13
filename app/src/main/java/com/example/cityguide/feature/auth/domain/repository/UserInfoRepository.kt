package com.example.cityguide.feature.auth.domain.repository

import com.example.cityguide.feature.auth.domain.model.UserData

interface UserInfoRepository {
    suspend fun getProfileUrl(): String

    suspend fun getProfileName(): String

    suspend fun getEmail(): String

    suspend fun getUserId(): String?

    fun isUserLoggedIn():Boolean

    fun getSignedInUser(): UserData?
}