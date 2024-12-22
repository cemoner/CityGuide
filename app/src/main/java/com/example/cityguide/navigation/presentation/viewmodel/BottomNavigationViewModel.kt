package com.example.cityguide.navigation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.main.util.IsLoggedInSingleton
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel
    @Inject
    constructor(
        private val navigator: AppNavigator,
    ) : ViewModel() {
        fun navigation(label: String) {
            when (label.lowercase(Locale.ROOT)) {
                "home" -> {
                    noArgNavigation(Destination.Home())
                }

                "favorites" -> {
                    noArgNavigation(Destination.Favorites())
                }
                "profile" -> {
                    if (IsLoggedInSingleton.getIsLoggedIn()) {
                        navigationToProfileWith()
                    } else {
                        noArgNavigation(Destination.Login())
                    }
                }
            }
        }

        private fun noArgNavigation(destination: String) {
            navigator.tryNavigateTo(
                destination,
            )
        }

        private fun navigationToProfileWith() {
            navigator.tryNavigateTo(
                route = Destination.Profile(),
                popUpToRoute = Destination.Login(),
            )
        }
    }
