package com.example.cityguide.feature.favorites.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.SideEffect
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.UiAction
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.UiState
import com.example.cityguide.feature.favorites.presentation.viewmodel.FavoritesViewModel
import com.example.cityguide.feature.home.presentation.component.PlaceCard
import com.example.cityguide.mvi.CollectSideEffect
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoritesScreen(){
    val viewModel: FavoritesViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    FavoritesContent(uiState,uiAction,sideEffect)
}

@Composable
fun FavoritesContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
) {

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
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(12.dp).fillMaxSize()
    ){
        if(uiState.places.isEmpty()){
            item{
                Text(text = "No favorites found")
            }
        }
        else {
            items(uiState.places.size){ index ->
                if(uiState.places[index].place.photoReferences[0] != ""){
                    PlaceCard(
                        place = uiState.places[index].place,
                        goToDetails = {onAction(UiAction.OnPlaceClick(uiState.places[index].place.placeId))},
                        favoriteClick = {onAction(UiAction.onFavoriteClick(uiState.places[index].place))}
                    )
                }
            }
        }
    }


}