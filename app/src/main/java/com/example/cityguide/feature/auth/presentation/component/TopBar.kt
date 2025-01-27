package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(appNavigator: AppNavigator, switchToEditAction:() -> Unit, doneEditAction:() -> Unit,uiState:ProfilePageContract.UiState){
    TopAppBar(title = {},
        navigationIcon = { BackButton({appNavigator.tryNavigateBack()}) },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.topAppBarColors(containerColor = background)
        }
        ,actions = {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                if(uiState.editState){
                    Text(
                        text = "Done",
                        modifier = Modifier.clickable(onClick = doneEditAction),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                else {
                    IconButton(
                        onClick = switchToEditAction,
                        modifier = Modifier.size(36.dp)
                            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                            .align(Alignment.Center).padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
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
