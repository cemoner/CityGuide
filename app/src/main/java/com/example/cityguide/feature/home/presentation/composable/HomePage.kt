package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.home.presentation.viewmodel.HomePageViewModel


@Composable
fun HomeScreen(){
    val viewModel: HomePageViewModel = hiltViewModel()
    HomeContent(viewModel)
}


@Composable
fun HomeContent(viewModel: HomePageViewModel){
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(8.dp),
                columns = StaggeredGridCells.Fixed(2)
            ) {
                item{
                    Text("Hello!")
                }
                item{
                    Text("Hello!")
                }
            }
        }
    }
}