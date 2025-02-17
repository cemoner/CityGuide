package com.example.cityguide.feature.profile.data.repository

import com.example.cityguide.feature.profile.domain.repository.UserInfoUpdateRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.net.Uri


class UserInfoUpdateRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserInfoUpdateRepository {
    private val currentUser
        get() = firebaseAuth.currentUser!!
    override suspend fun updateName(name: String): Result<Unit> {
        return try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            currentUser.updateProfile(profileUpdates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun updateSurname(surname: String): Result<Unit> {
        return try {
            val updatedName = currentUser.displayName?.let { "$it $surname" } ?: surname
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(updatedName)
                .build()
            currentUser.updateProfile(profileUpdates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun updateProfileImageUrl(profileImageUrl: String): Result<Unit> {
        return try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build()
            currentUser.updateProfile(profileUpdates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
