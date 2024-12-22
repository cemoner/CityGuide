package com.example.cityguide.feature.home.presentation.composable

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.cityguide.R
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.HomePageViewModel
import com.example.cityguide.main.util.IsLoggedInSingleton
import com.example.cityguide.main.util.LanguageChangeHelper
import com.example.cityguide.mvi.unpack
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.util.Locale


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
    TopAppBar(title = {
        Title(uiState,onAction)
    },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.topAppBarColors(containerColor = background)
        },
        actions = { LanguageSelector() }
    )
}


@Composable
fun Title(uiState: UiState, onAction: (UiAction) -> Unit,){
    val isLoggedIn = IsLoggedInSingleton.getIsLoggedIn()
    val rememberedLoginState = remember { mutableStateOf(isLoggedIn) }

    val isSuccess = uiState is UiState.Success
    val name = (uiState as? UiState.Success)?.name
    val profileImageUrl = (uiState as? UiState.Success)?.profileImageUrl

    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.background(MaterialTheme.colorScheme.surface, shape = CircleShape).padding(horizontal = 2.dp))
    {
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
}


@Composable
fun LanguageSelector() {
    var isDropdownOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val applicationContext = context.applicationContext

    val languageChangeHelper by lazy {
        LanguageChangeHelper()
    }

   var selectedLanguage by remember { mutableStateOf(languageChangeHelper.getLanguageCode(applicationContext)) }

    val languageFlags = mapOf(
        "en" to R.drawable.flag_english,
        "tr" to R.drawable.flag_turkish,
        "de" to R.drawable.flag_german
    )
    val languageNames = mapOf(
        "en" to "English",
        "tr" to "Türkçe",
        "de" to "Deutsch"
    )

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.clickable { isDropdownOpen = !isDropdownOpen }, verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { isDropdownOpen = !isDropdownOpen }) {
                Image(
                    painter = painterResource(id = languageFlags[selectedLanguage]!!),
                    contentDescription = "Current Language",
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(languageNames[selectedLanguage] ?: "Unknown", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground)
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                contentDescription = ""
            )
        }
        Box(
            modifier = Modifier
                .offset(y = (32.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
                .align(Alignment.TopCenter)
        ) {
            DropdownMenu(
                expanded = isDropdownOpen,
                onDismissRequest = { isDropdownOpen = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                languageFlags.forEach { (languageCode, flagRes) ->
                    DropdownMenuItem(
                        onClick = {
                            selectedLanguage = languageCode
                            languageChangeHelper.changeLanguage(applicationContext,languageCode)
                            isDropdownOpen = false
                        },
                        enabled = if (selectedLanguage == languageCode) false else true,
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = flagRes),
                                    contentDescription = "Language Flag",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(languageNames[languageCode] ?: "Unknown", style = MaterialTheme.typography.bodyMedium,
                                    color = if (selectedLanguage == languageCode) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onBackground)
                            }
                        }
                    )
                }
            }
        }
    }
}
