package com.example.cityguide.feature.auth.domain.usecase

import com.example.cityguide.feature.auth.domain.model.UserData
import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetSıgnedInUserUseCase @Inject constructor(private val userInfoRepository: UserInfoRepository) {
    suspend operator fun invoke(): UserData? = userInfoRepository.getSignedInUser()
}