package com.example.cityguide.main.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.GetEmailUseCase
import com.example.cityguide.feature.auth.domain.usecase.GetProfileNameUseCase
import com.example.cityguide.feature.auth.domain.usecase.GetProfileUrlUseCase
import com.example.cityguide.feature.auth.domain.usecase.IsUserLoggedInUseCase
import com.example.cityguide.main.presentation.contract.MainContract.SideEffect
import com.example.cityguide.main.presentation.contract.MainContract.UiAction
import com.example.cityguide.main.presentation.contract.MainContract.UiState
import com.example.cityguide.main.util.ProfileEmailSingleton
import com.example.cityguide.main.util.ProfilePhoneNumberSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    @ApplicationContext private val context: Context,
    val appNavigator: AppNavigator,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getProfileUrlUseCase: GetProfileUrlUseCase,
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getEmailUseCase: GetEmailUseCase
) : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {
    val navigationChannel = appNavigator.navigationChannel


    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

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


    private fun tryNavigateTo(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    private fun tryNavigateBack(){
        viewModelScope.launch {
            appNavigator.tryNavigateBack()
        }
    }
}

private fun initialUiState():UiState = UiState.Loading
