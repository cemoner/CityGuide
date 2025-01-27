package com.example.cityguide.feature.auth.domain.usecase

import android.content.Intent
import com.example.cityguide.feature.auth.domain.repository.GoogleAuthRepository
import javax.inject.Inject

class GoogleSignInWithIntentUseCase @Inject constructor(private val googleSignInRepository: GoogleAuthRepository) {
    suspend operator fun invoke(intent:Intent) = googleSignInRepository.signInWithIntent(intent)
}