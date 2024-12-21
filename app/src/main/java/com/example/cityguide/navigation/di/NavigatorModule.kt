package com.example.cityguide.navigation.di

import com.example.cityguide.navigation.navigator.AppNavigator
import com.example.cityguide.navigation.navigator.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NavigatorModule {
    @Provides
    @Singleton
    fun providesNavigator(): AppNavigator = AppNavigatorImpl()
}
