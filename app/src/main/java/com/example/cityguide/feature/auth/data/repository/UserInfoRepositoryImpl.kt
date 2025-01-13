package com.example.cityguide.feature.auth.data.repository

import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): UserInfoRepository  {
    override suspend fun getProfileUrl(): String = firebaseAuth.currentUser?.photoUrl.toString()

    override suspend fun getProfileName(): String = firebaseAuth.currentUser?.displayName.toString()

    override fun isUserLoggedIn():Boolean = firebaseAuth.currentUser != null
}