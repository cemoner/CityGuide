package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(email: String): Result<Unit> = authenticationRepository.sendPasswordResetEmail(email)
}