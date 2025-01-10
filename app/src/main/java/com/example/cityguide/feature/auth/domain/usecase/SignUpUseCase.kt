package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(name:String,email: String, password: String): Result<FirebaseUser> = authenticationRepository.signUpWithEmailAndPassword(name,email,password)
}