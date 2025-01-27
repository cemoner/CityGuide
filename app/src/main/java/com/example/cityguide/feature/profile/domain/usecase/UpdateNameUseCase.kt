package com.example.cityguide.feature.profile.domain.usecase

import com.example.cityguide.feature.profile.domain.repository.UserInfoUpdateRepository
import javax.inject.Inject

class UpdateNameUseCase @Inject constructor(private val userInfoUpdateRepository: UserInfoUpdateRepository) {
    suspend operator fun invoke(name:String) = userInfoUpdateRepository.updateName(name)
}