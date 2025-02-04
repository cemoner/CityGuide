package com.example.cityguide.main.presentation.activity

import DynamicBottomBar
import DynamicTopBar
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cityguide.feature.auth.presentation.composable.ForgotPasswordScreen
import com.example.cityguide.feature.auth.presentation.composable.SignInScreen
import com.example.cityguide.feature.auth.presentation.composable.SignUpScreen
import com.example.cityguide.feature.home.presentation.composable.CategoryPageScreen
import com.example.cityguide.feature.home.presentation.composable.HomeScreen
import com.example.cityguide.feature.profile.presentation.composable.ProfileScreen
import com.example.cityguide.feature.profile.presentation.viewmodel.ProfilePageViewModel
import com.example.cityguide.main.util.LocationException
import com.example.cityguide.main.presentation.contract.MainContract.SideEffect
import com.example.cityguide.main.presentation.contract.MainContract.UiAction
import com.example.cityguide.main.presentation.contract.MainContract.UiState
import com.example.cityguide.main.presentation.viewmodel.MainViewModel
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.CountryNameSingleton
import com.example.cityguide.main.util.hasLocationPermission
import com.example.cityguide.mvi.unpack
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import com.example.cityguide.navigation.navigator.NavigationIntent
import com.example.cityguide.navigation.presentation.composable.NavHost
import com.example.cityguide.navigation.presentation.composable.composable
import com.example.cityguide.ui.theme.CityGuideTheme
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private var context = this

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private var isDialogShown = false


    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private val mainViewModel: MainViewModel by viewModels()
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                fetchLocationAndSetCity()
            } else {
                requestLocationPermission()
            }
        }
        window.setBackgroundDrawableResource(android.R.color.transparent)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                !mainViewModel.isInitialized.value
            }
        }
        if(!context.hasLocationPermission()){
            requestLocationPermission()
        }
        else {
            fetchLocationAndSetCity()
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

    private suspend fun getCityAndCountryName(latitude: Double, longitude: Double): Pair<String?,String?>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                if (Geocoder.isPresent()) {
                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1,
                        object : Geocoder.GeocodeListener {
                            override fun onGeocode(addresses: List<android.location.Address>) {
                                val address = addresses.firstOrNull()
                                val city = address?.locality ?: address?.adminArea
                                val country = address?.countryName
                                continuation.resume(Pair(city, country))
                            }

                            override fun onError(errorMessage: String?) {
                                continuation.resumeWithException(
                                    RuntimeException(errorMessage ?: "Unknown geocoding error")
                                )
                            }
                        }
                    )
                } else {
                    continuation.resumeWithException(RuntimeException("Geocoder is not present on this device."))
                }
            }
        } else {
            withContext(Dispatchers.IO) {
                val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val address = addresses?.firstOrNull()
                    val city = address?.locality ?: address?.adminArea
                    val country = address?.countryName
                    Pair(city, country)
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndSetCity() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    lifecycleScope.launch {
                        val cityName = getCityAndCountryName(it.latitude, it.longitude)
                        cityName?.let { (city,country) ->
                            if (city != null) {
                                CityNameSingleton.setName(city)
                            }
                            if (country != null) {
                                CountryNameSingleton.setName(country)
                            }
                        }
                        Toast.makeText(this@MainActivity, "${CityNameSingleton.cityName.value}", Toast.LENGTH_LONG).show()
                    }
                } ?: run {
                    Log.e("MainActivity", "Location unavailable")
                    throw LocationException("Location Unavailable")
                }
            }
            .addOnFailureListener {
                Log.e("MainActivity", "Failed to fetch location: ${it.message}")
                throw it
            }
    }


    override fun onResume() {
        super.onResume()
        if (hasLocationPermission()) {
            fetchLocationAndSetCity()
        } else {
            if (!isDialogShown) {
                showGoToSettingsDialog()
                isDialogShown = true
            }
        }
    }


    private fun requestLocationPermission() {
        when {
            hasLocationPermission() -> fetchLocationAndSetCity()
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> showGoToSettingsDialog()
            else -> showGoToSettingsDialog()
        }
    }

    private fun showGoToSettingsDialog() {
        if (!isDialogShown) {
            AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("You need to grant location permission manually. Please go to settings and enable it.")
                .setPositiveButton("Go to Settings") { dialog, _ ->
                    dialog.dismiss()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    isDialogShown = false
                }
                .setNegativeButton("Exit") { _, _ -> finish() }
                .setCancelable(false)
                .show()
            isDialogShown = true
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

                    composable(destination = Destination.CategoryPage) {
                        CategoryPageScreen()
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
