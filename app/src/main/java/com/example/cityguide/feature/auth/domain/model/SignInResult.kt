package com.example.cityguide.feature.auth.domain.model

data class SignInResult(
    val data: UserData?,
    val error:String?
)


data class UserData(
    val userId:String,
    val username:String?,
    val profilePictureUrl:String?
)