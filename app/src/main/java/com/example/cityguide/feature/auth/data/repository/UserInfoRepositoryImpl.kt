package com.example.cityguide.feature.auth.data.repository

import com.example.cityguide.feature.auth.domain.model.UserData
import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): UserInfoRepository  {
    override suspend fun getProfileUrl(): String = firebaseAuth.currentUser?.photoUrl.toString()

    override suspend fun getProfileName(): String = firebaseAuth.currentUser?.displayName.toString()

    override suspend fun getEmail(): String = firebaseAuth.currentUser?.email.toString()

    override fun isUserLoggedIn():Boolean = firebaseAuth.currentUser != null

    override fun getSignedInUser(): UserData? = firebaseAuth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}