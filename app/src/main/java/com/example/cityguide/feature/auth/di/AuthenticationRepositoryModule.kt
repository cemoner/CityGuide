package com.example.cityguide.feature.auth.di

import android.content.Context
import com.example.cityguide.feature.auth.data.repository.AuthenticationRepositoryImpl
import com.example.cityguide.feature.auth.data.repository.GoogleAuthRepositoryImpl
import com.example.cityguide.feature.auth.data.repository.UserInfoRepositoryImpl
import com.example.cityguide.feature.auth.domain.repository.AuthenticationRepository
import com.example.cityguide.feature.auth.domain.repository.GoogleAuthRepository
import com.example.cityguide.feature.auth.domain.repository.UserInfoRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationRepositoryModule {

    @Provides
    fun providesAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository = AuthenticationRepositoryImpl(firebaseAuth)

    @Provides
    fun providesUserInfoRepository(firebaseAuth: FirebaseAuth): UserInfoRepository = UserInfoRepositoryImpl(firebaseAuth)

    @Provides
    fun providesGoogleAuthRepository(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth,
        oneTapClient: SignInClient
    ): GoogleAuthRepository = GoogleAuthRepositoryImpl(context, oneTapClient, firebaseAuth)
}