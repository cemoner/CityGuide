package com.example.cityguide.main.presentation.viewmodel

import ProfileImageUrlSingleton
import ProfileNameSingleton
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.GetEmailUseCase
import com.example.cityguide.feature.auth.domain.usecase.GetProfileNameUseCase
import com.example.cityguide.feature.auth.domain.usecase.GetProfileUrlUseCase
import com.example.cityguide.feature.auth.domain.usecase.IsUserLoggedInUseCase
import com.example.cityguide.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.example.cityguide.main.presentation.contract.MainContract.SideEffect
import com.example.cityguide.main.presentation.contract.MainContract.UiAction
import com.example.cityguide.main.presentation.contract.MainContract.UiState
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.Coordinates
import com.example.cityguide.main.util.CountryNameSingleton
import com.example.cityguide.main.util.LocationDetail
import com.example.cityguide.main.util.LocationException
import com.example.cityguide.main.util.ProfileEmailSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.navigator.AppNavigator
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class MainViewModel
@Inject
constructor(
    @ApplicationContext private val context: Context,
    val appNavigator: AppNavigator,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getProfileUrlUseCase: GetProfileUrlUseCase,
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getEmailUseCase: GetEmailUseCase,
) : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {
    val navigationChannel = appNavigator.navigationChannel


    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _isDataRetrieved = MutableStateFlow(false)
    val isDataRetrieved: StateFlow<Boolean> = _isDataRetrieved.asStateFlow()

    init {
        viewModelScope.launch {
            if (isNetworkAvailable()){
                if (isUserLoggedIn()) {
                    updateUiState(UiState.Success(true))
                    handleLoggedInUser()
                }
                else {
                    updateUiState(UiState.Success(false))
                }
            }
            else {
                updateUiState(UiState.Error("No internet connection"))
            }
        }
    }

    override fun onAction(action: UiAction) {
        when(action){
            UiAction.OnBackButtonClick -> tryNavigateBack()
        }
    }

    fun markInitializationComplete() {
        _isInitialized.value = true
    }
    
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    private fun isUserLoggedIn():Boolean{
        return isUserLoggedInUseCase()
    }

    private suspend fun handleLoggedInUser() {
        val profileUrl = getProfileUrlUseCase()
        println("Profile URL: $profileUrl")
        ProfileImageUrlSingleton.setProfileImageUrl(profileUrl)

        val profileName = getProfileNameUseCase()
        println("Profile Name: $profileName")
        ProfileNameSingleton.setName(profileName)

        val email = getEmailUseCase()
        println("Profile Email: $email") // Debugging log
        ProfileEmailSingleton.setEmail(email)
    }


    private fun tryNavigateBack(){
        viewModelScope.launch {
            appNavigator.tryNavigateBack()
        }
    }

    suspend fun getCityAndCountryName(latitude: Double, longitude: Double): LocationDetail? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                val geocoder = Geocoder(context, Locale.getDefault())
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
                                val result = LocationDetail(city, country, latitude, longitude)
                                continuation.resume(result)
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
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val address = addresses?.firstOrNull()
                    val city = address?.locality ?: address?.adminArea
                    val country = address?.countryName
                    LocationDetail(city, country, latitude, longitude)
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchLocationAndSetCity(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModelScope.launch {
                        val cityName = getCityAndCountryName(it.latitude, it.longitude)
                        cityName?.let { (city,country,longtitude,latitude) ->
                            if (city != null) {
                                CityNameSingleton.setName(city)
                            }
                            if (country != null) {
                                CountryNameSingleton.setName(country)
                            }
                            Log.d("Coordinates", "Latitude: $latitude, Longitude: $longtitude")
                            Coordinates.setCoordinates(longtitude,latitude)
                            _isDataRetrieved.value = true
                        }
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

}

private fun initialUiState():UiState = UiState.Loading
