package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguide.navigation.navigator.AppNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(appNavigator: AppNavigator){
    TopAppBar(title = {},
        navigationIcon = { BackButton({appNavigator.tryNavigateBack()}) },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.topAppBarColors(containerColor = background)
        }
    )
}

@Composable
fun BackButton(
    onBackClick: () -> Unit,

) {
    Box(modifier = Modifier.padding(horizontal = 12.dp)){
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(36.dp).background(color = MaterialTheme.colorScheme.surface,shape = CircleShape).align(Alignment.Center).padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
