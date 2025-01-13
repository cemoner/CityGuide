package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(private val userInfoRepository: UserInfoRepository) {
    operator fun invoke(): Boolean = userInfoRepository.isUserLoggedIn()
}