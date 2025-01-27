package com.example.cityguide.feature.profile.domain.usecase

import com.example.cityguide.feature.profile.domain.repository.UserInfoUpdateRepository
import javax.inject.Inject

class UpdateSurnameUseCase @Inject constructor(private val userInfoUpdateRepository: UserInfoUpdateRepository) {
    suspend operator fun invoke(surname:String) = userInfoUpdateRepository.updateSurname(surname)
}