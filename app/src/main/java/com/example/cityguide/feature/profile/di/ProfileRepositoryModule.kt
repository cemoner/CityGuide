package com.example.cityguide.feature.profile.di


import com.example.cityguide.feature.profile.data.repository.UserInfoUpdateRepositoryImpl
import com.example.cityguide.feature.profile.domain.repository.UserInfoUpdateRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProfileRepositoryModule {

    @Provides
    fun providesProfileRepository(firebaseAuth: FirebaseAuth): UserInfoUpdateRepository = UserInfoUpdateRepositoryImpl(firebaseAuth)
}