package com.example.cityguide.feature.auth.di

import com.example.cityguide.feature.auth.data.repository.AuthenticationRepositoryImpl
import com.example.cityguide.feature.auth.data.repository.UserInfoRepositoryImpl
import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationRepositoryModule {

    @Provides
    fun providesAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository = AuthenticationRepositoryImpl(firebaseAuth)

    @Provides
    fun providesUserInfoRepository(firebaseAuth: FirebaseAuth): UserInfoRepository = UserInfoRepositoryImpl(firebaseAuth)
}