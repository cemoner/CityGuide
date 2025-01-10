package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetProfileNameUseCase @Inject constructor(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(): String = userInfoRepository.getProfileName()
}