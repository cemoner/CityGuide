package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.HomePageViewModel
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


