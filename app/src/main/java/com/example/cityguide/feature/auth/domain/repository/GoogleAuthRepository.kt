package com.example.cityguide.feature.auth.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.example.cityguide.feature.auth.domain.model.SignInResult

interface GoogleAuthRepository {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signOut()

}