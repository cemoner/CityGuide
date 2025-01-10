package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> = authenticationRepository.signInWithEmailAndPassword(email,password)
}