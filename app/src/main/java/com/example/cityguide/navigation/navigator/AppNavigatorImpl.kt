package com.example.cityguide.navigation.navigator

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class AppNavigatorImpl
    @Inject
    constructor() : AppNavigator {
        override val navigationChannel =
            Channel<NavigationIntent>(
                capacity = Int.MAX_VALUE,
                onBufferOverflow = BufferOverflow.DROP_LATEST,
            )

        override suspend fun navigateBack(
            route: String?,
            inclusive: Boolean,
        ) {
            navigationChannel.send(
                NavigationIntent.NavigateBack(
                    route = route,
                    inclusive = inclusive,
                ),
            )
        }

        override fun tryNavigateBack(route: String?) {
            navigationChannel.trySend(
                NavigationIntent.NavigateBack(
                    route = route,
                    inclusive = route != null,
                ),
            )
        }

        override suspend fun navigateTo(
            route: String,
            popUpToRoute: String?,
            inclusive: Boolean,
            isSingleTop: Boolean,
            saveState: Boolean,
            restoreState: Boolean,
        ) {
            navigationChannel.send(
                NavigationIntent.NavigateTo(
                    route = route,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    isSingleTop = isSingleTop,
                    restoreState = restoreState,
                    saveState = saveState,
                ),
            )
        }

        override fun clearBackStack() {
            navigationChannel.trySend(NavigationIntent.ClearBackStack)
        }

        override fun tryNavigateTo(
            route: String,
            popUpToRoute: String?,
            inclusive: Boolean,
            isSingleTop: Boolean,
            saveState: Boolean,
            restoreState: Boolean,
        ) {
            navigationChannel.trySend(
                NavigationIntent.NavigateTo(
                    route = route,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    isSingleTop = isSingleTop,
                    restoreState = restoreState,
                    saveState = saveState,
                ),
            )
        }
    }
