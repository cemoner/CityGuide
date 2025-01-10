package com.example.cityguide.feature.auth.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {

    suspend fun signUpWithEmailAndPassword(name:String,email: String, password: String): Result<FirebaseUser>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser>

    fun isUserLoggedIn():Boolean

}