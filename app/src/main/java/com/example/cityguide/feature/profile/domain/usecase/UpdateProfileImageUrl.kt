package com.example.cityguide.feature.profile.domain.usecase

import com.example.cityguide.feature.profile.domain.repository.UserInfoUpdateRepository
import javax.inject.Inject

class UpdateProfileImageUrl @Inject constructor(private val userInfoUpdateRepository: UserInfoUpdateRepository) {
    suspend operator fun invoke(profileImageUrl:String) = userInfoUpdateRepository.updateProfileImageUrl(profileImageUrl)
}