package com.example.cityguide.main.presentation.activity

import DynamicBottomBar
import DynamicTopBar
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cityguide.feature.auth.domain.usecase.SignOutUseCase
import com.example.cityguide.feature.auth.presentation.component.TopBar
import com.example.cityguide.feature.auth.presentation.composable.ForgotPasswordScreen
import com.example.cityguide.feature.auth.presentation.composable.OTPVerificationScreen
import com.example.cityguide.feature.auth.presentation.composable.SignInScreen
import com.example.cityguide.feature.auth.presentation.composable.SignUpScreen
import com.example.cityguide.feature.home.presentation.component.TopBarHome
import com.example.cityguide.feature.home.presentation.composable.HomeScreen
import com.example.cityguide.feature.map.presentation.MapScreen
import com.example.cityguide.feature.profile.presentation.composable.ProfileScreen
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract
import com.example.cityguide.feature.profile.presentation.viewmodel.ProfilePageViewModel
import com.example.cityguide.main.presentation.contract.MainContract.SideEffect
import com.example.cityguide.main.presentation.contract.MainContract.UiAction
import com.example.cityguide.main.presentation.contract.MainContract.UiState
import com.example.cityguide.main.presentation.viewmodel.MainViewModel
import com.example.cityguide.mvi.unpack
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import com.example.cityguide.navigation.navigator.NavigationIntent
import com.example.cityguide.navigation.presentation.composable.NavHost
import com.example.cityguide.navigation.presentation.composable.NavigationBar
import com.example.cityguide.navigation.presentation.composable.composable
import com.example.cityguide.ui.theme.CityGuideTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var signOutUseCase: SignOutUseCase


    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(android.R.color.transparent)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                !mainViewModel.isInitialized.value
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CityGuideTheme {
                AppScreen(navController)
            }
        }
    }
}


@Composable
fun AppScreen(navController: NavHostController){
    val viewModel: MainViewModel = hiltViewModel<MainViewModel>()
    val (uiState,onAction,sideEffect) = viewModel.unpack()
    Box(modifier = Modifier
        .systemBarsPadding()
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        AppContent(viewModel,uiState,onAction,sideEffect,viewModel.appNavigator,navController)
    }
}


@Composable
fun AppContent(
    viewModel: MainViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    navigator: AppNavigator,
    navController: NavHostController
) {
    val isInitialized by viewModel.isInitialized.collectAsState()
    LaunchedEffect(isInitialized) {
        if (!isInitialized) {
            viewModel.markInitializationComplete()
        }
    }
    val profileViewModel: ProfilePageViewModel = hiltViewModel()
    val (profileUiState,profileOnAction,profileSideEffect) = profileViewModel.unpack()

    when(uiState){
        is UiState.Error -> TODO()
        is UiState.Loading -> {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            NavigationEffects(
                navigationChannel = viewModel.navigationChannel,
                navHostController = navController,
            )

            Scaffold(
                topBar =
                {
                    DynamicTopBar(navController = navController, appNavigator = navigator,profileUiState,profileOnAction)
                },

                bottomBar = {
                    DynamicBottomBar(navController = navController)
                },
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).systemBarsPadding(),
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = if(uiState.isUserLoggedIn) Destination.Home else Destination.SignIn,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(destination = Destination.Home) {
                        HomeScreen()
                    }
                    composable(destination = Destination.Profile) {
                        ProfileScreen(profileUiState,profileOnAction,profileSideEffect,profileViewModel)
                    }
                    composable(destination = Destination.SignIn) {
                        SignInScreen()
                    }
                    composable(destination = Destination.SignUp) {
                        SignUpScreen()
                    }

                    composable(destination = Destination.ForgotPassword) {
                        ForgotPasswordScreen()
                    }

                    composable(destination = Destination.Favorites) {
                    }

                }
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
                    navHostController.navigate(Destination.SignIn()){
                        launchSingleTop
                    }
                }
            }
        }
    }
}
