package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.home.presentation.component.PlaceCard
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.CategoryPageViewModel
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryPageScreen(){
    val viewModel: CategoryPageViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    CategoryPageContent(viewModel,uiState,uiAction,sideEffect)
}

@Composable
fun CategoryPageContent(
    viewModel: CategoryPageViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp).fillMaxSize()
    ) {
        when(uiState){
            is UiState.Error -> item{Text(text = uiState.message, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineLarge )}
            is UiState.Loading -> item{ CircularProgressIndicator() }
            is UiState.Success -> {
                items(uiState.places.size){
                    PlaceCard(uiState.places[it])
                }
            }
        }
    }
}