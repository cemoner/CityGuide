package com.example.cityguide.feature.auth.data.repository

import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl
    @Inject constructor(private val firebaseAuth: FirebaseAuth)
    :AuthenticationRepository {

    override suspend fun signUpWithEmailAndPassword(
        name:String,
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw NullPointerException("User is null after sign-up")
            user.updateProfile(
                userProfileChangeRequest {
                    displayName = name
                }
            ).await()
            Result.success(user)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }


    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            Result.success(result.user ?: throw NullPointerException("User is null after sign-in"))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}