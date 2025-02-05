package com.example.cityguide.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
) {
    object Home : NavItem(Icons.Outlined.Home, Icons.Filled.Home, "home")

    object Favorites : NavItem(Icons.Outlined.Favorite, Icons.Filled.Favorite, "favorites")

    object Profile : NavItem(Icons.Outlined.Person, Icons.Filled.Person, "profile")

    companion object {
        fun navigationBarItems(): List<NavItem> = listOf(Home, Favorites, Profile)
    }
}
