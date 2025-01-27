package com.example.cityguide.feature.profile.domain.repository

interface UserInfoUpdateRepository {
    suspend fun updateName(name:String):Result<Unit>

    suspend fun updateSurname(surname:String):Result<Unit>

    suspend fun updateProfileImageUrl(profileImageUrl:String):Result<Unit>



}