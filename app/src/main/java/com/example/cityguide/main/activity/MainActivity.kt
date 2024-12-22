package com.example.cityguide.main.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cityguide.feature.home.presentation.composable.HomeScreen
import com.example.cityguide.feature.profile.presentation.composable.ProfileScreen
import com.example.cityguide.main.viewmodel.MainViewModel
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.NavigationIntent
import com.example.cityguide.navigation.presentation.composable.NavHost
import com.example.cityguide.navigation.presentation.composable.composable
import com.example.cityguide.ui.theme.CityGuideTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(android.R.color.transparent)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                return@setKeepOnScreenCondition false
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CityGuideTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen(){
    val viewModel:MainViewModel = hiltViewModel<MainViewModel>()
    Box(modifier = Modifier.systemBarsPadding().fillMaxSize().background(MaterialTheme.colorScheme.background)){
        AppContent(viewModel)
    }
}


@Composable
fun AppContent(
    viewModel:MainViewModel
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController,
    )
    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).systemBarsPadding(),
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Destination.Home,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(destination = Destination.Home) {
                HomeScreen()
            }
            composable(destination = Destination.Profile) {
                ProfileScreen()
            }
            composable(destination = Destination.Login) {
            }
            composable(destination = Destination.Register) {
            }
            composable(destination = Destination.Favorites) {
            }

            composable(destination = Destination.Cart) {
            }

            composable(destination = Destination.LocationDetail) {
            }
        }
    }
}


@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) {
                                saveState = intent.saveState
                                inclusive = intent.inclusive
                            }
                        }
                        restoreState = intent.restoreState
                    }
                }
                is NavigationIntent.ClearBackStack -> {
                    navHostController.popBackStack(navHostController.graph.id, false)
                    navHostController.navigate(Destination.Home())
                }
            }
        }
    }
}
