package com.example.cityguide.navigation.navigator

import kotlinx.coroutines.channels.Channel

interface AppNavigator {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun tryNavigateBack(route: String? = null)

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
    )

    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
    )

    fun clearBackStack()
}

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
        val saveState: Boolean = false,
        val restoreState: Boolean = false,
    ) : NavigationIntent()

    object ClearBackStack : NavigationIntent()
}
