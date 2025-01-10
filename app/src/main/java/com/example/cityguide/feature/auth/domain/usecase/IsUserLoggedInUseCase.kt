package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(): Boolean = authenticationRepository.isUserLoggedIn()
}