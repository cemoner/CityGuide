package com.example.cityguide.feature.home.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.cityguide.navigation.navigator.AppNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(
    appNavigator: AppNavigator,
){
    TopAppBar(title = {
        Title(appNavigator)
    },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.topAppBarColors(containerColor = background)
        },
        actions = { LanguageSelector() }
    )
}

