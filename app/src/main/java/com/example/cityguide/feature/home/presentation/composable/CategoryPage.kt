package com.example.cityguide.feature.home.presentation.composable

import SortDropdown
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.home.presentation.component.PlaceCard
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.CategoryPageViewModel
import com.example.cityguide.mvi.CollectSideEffect
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryPageScreen(){
    val viewModel: CategoryPageViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    CategoryPageContent(uiState,uiAction,sideEffect)
}

@Composable
fun CategoryPageContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
){
    val context = LocalContext.current
    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
        if(uiState is UiState.Success) Arrangement.Top else Arrangement.Center,
        modifier = Modifier.padding(12.dp).fillMaxSize()
    ) {

        when(uiState){
            is UiState.Error -> item{Text(text = uiState.message, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium )}
            is UiState.Loading -> item{ CircularProgressIndicator() }
            is UiState.Success -> {
                item {
                    TextField(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent)
                            .clip(RoundedCornerShape(18.dp)),
                        value = uiState.searchText,
                        onValueChange = {
                            onAction(UiAction.OnSearchTextChange(it))
                        },
                        singleLine = true,
                        colors =
                        TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                        placeholder = {
                            Text(
                                text = "Search",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            )
                        },
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                                .weight(1f),
                            text = uiState.categoryName,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start
                        )
                        SortDropdown(
                            currentSort = uiState.currentSort,
                            onSortSelected = { sortType ->
                                onAction(UiAction.OnSortClick(sortType))
                            }
                        )
                    }
                }
                items(uiState.searchPlaces.size){ index ->
                    if(uiState.searchPlaces[index].photoReferences[0] != ""){
                        uiState.searchPlaces[index].photoReferences.firstOrNull()?.let {
                            PlaceCard(
                                place = uiState.searchPlaces[index],
                                goToDetails = {onAction(UiAction.OnPlaceClick(uiState.searchPlaces[index].placeId))},
                                favoriteClick = {onAction(UiAction.onFavoriteClick(uiState.searchPlaces[index]))}
                            )
                        }
                    }
                }
            }
        }
    }
}