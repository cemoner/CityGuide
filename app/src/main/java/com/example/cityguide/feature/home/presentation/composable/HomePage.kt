package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.R
import com.example.cityguide.feature.home.presentation.component.Weather
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.HomePageViewModel
import com.example.cityguide.mvi.unpack
import com.example.cityguide.ui.theme.DarkActionColor
import com.example.cityguide.ui.theme.DarkTextColor
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
) {
    val images: List<Painter> = listOf(
        painterResource(R.drawable.gastronomy),
        painterResource(R.drawable.night_life),
        painterResource(R.drawable.cultural_and_enterteinment),
        painterResource(R.drawable.accommodations),
        painterResource(R.drawable.parks_and_nature),
        painterResource(R.drawable.religious_sites),
        painterResource(R.drawable.sports_and_recreation),
        painterResource(R.drawable.health_and_wellness)
    )

    val categoryNames: List<String> = listOf(
        stringResource(R.string.gastronomy),
        stringResource(R.string.night_life),
        stringResource(R.string.cultural_and_entertainment),
        stringResource(R.string.accommodations),
        stringResource(R.string.parks_and_nature),
        stringResource(R.string.religious_sites),
        stringResource(R.string.sports_and_recreation),
        stringResource(R.string.health_and_wellness)
    )

    val categoryInput: List<String> = listOf(
        "Gastronomy",
        "Night Life",
        "Cultural and Entertainment",
        "Accommodations",
        "Parks and Nature",
        "Religious Sites",
        "Sports and Recreation",
        "Health and Wellness"
    )

    val screenWidth: Dp = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth: Dp = screenWidth * 0.85f
    val horizontalPadding = (screenWidth - itemWidth) / 2

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = horizontalPadding / 4, end = horizontalPadding),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.explore_the),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row {
                        Text(
                            text = stringResource(R.string.beautiful),
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(R.string.world),
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = DarkActionColor
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = horizontalPadding / 4, end = horizontalPadding),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.categories),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(6.dp)) }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(start = horizontalPadding / 4, end = horizontalPadding),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categories.size) { index ->
                        Box(
                            modifier = Modifier
                                .width(itemWidth)
                                .padding(8.dp)
                                .clickable {
                                    onAction(UiAction.OnCategoryClick(categoryInput[index]))
                                }
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .align(Alignment.Center),
                                painter = images[index],
                                contentDescription = categoryInput[index],
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = categoryNames[index],
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp),
                                style = MaterialTheme.typography.titleLarge,
                                color = DarkTextColor
                            )
                        }
                    }
                }
            }

            item { Weather() }
        }
    }
}


