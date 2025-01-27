package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.GoogleAuthRepository
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val googleAuthRepository: GoogleAuthRepository) {
    suspend operator fun invoke() = googleAuthRepository.signIn()
}