package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.HomePageViewModel
import com.example.cityguide.main.util.IsLoggedInSingleton
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(){
    val viewModel: HomePageViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    HomeContent(viewModel,uiState,uiAction,sideEffect)
}


@Composable
fun HomeContent(
    viewModel: HomePageViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
){
    Scaffold(
        topBar = { TopBar(uiState,onAction,sideEffect) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(8.dp),
                columns = StaggeredGridCells.Fixed(2)
            ) {
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
){
    val isLoggedIn = IsLoggedInSingleton.getIsLoggedIn()
    val rememberedLoginState = remember { mutableStateOf(isLoggedIn) }

    val isSuccess = uiState is UiState.Success
    val name = (uiState as? UiState.Success)?.name
    val profileImageUrl = (uiState as? UiState.Success)?.profileImageUrl


    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.background(MaterialTheme.colorScheme.surface, shape = CircleShape).padding(horizontal = 2.dp)){
            IconButton(
                onClick = {onAction(UiAction.NavigateToProfile)},
                Modifier
                    .size(36.dp).padding(2.dp)
            ) {

                if (rememberedLoginState.value && isSuccess && profileImageUrl != null) {
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(36.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Default Profile Icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            if(rememberedLoginState.value && isSuccess){
                Text( modifier = Modifier.padding(end = 12.dp),
                    text = "$name",style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
            }
        }
    },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.topAppBarColors(containerColor = background)
        }
    )
}