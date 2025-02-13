package com.example.cityguide.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.domain.model.City
import com.example.cityguide.feature.home.domain.usecase.GetCitySuggestionsUseCase
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.CountryNameSingleton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitySuggestionsUseCase
):ViewModel() {
    private val _suggestions = MutableStateFlow<List<City>>(emptyList())
    val suggestions: StateFlow<List<City>> = _suggestions.asStateFlow()

    fun fetchCitySuggestions(namePrefix: String) = viewModelScope.launch {
        val result = getCitiesUseCase(namePrefix)
        if (result.isSuccess) {
            Log.d("CityViewModel", "Fetched cities: ${result.getOrThrow()}")
            _suggestions.value = result.getOrThrow().sortedWith(
                compareBy { levenshteinDistance(it.name.lowercase(), namePrefix.lowercase()) }
            )
            if(_suggestions.value.isEmpty()){
                _suggestions.value = emptyList()
            }
        } else {
            _suggestions.value = emptyList()
        }
    }


    fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) {
            for (j in 0..s2.length) {
                when {
                    i == 0 -> dp[i][j] = j
                    j == 0 -> dp[i][j] = i
                    s1[i - 1] == s2[j - 1] -> dp[i][j] = dp[i - 1][j - 1]
                    else -> dp[i][j] = 1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
                }
            }
        }
        return dp[s1.length][s2.length]
    }

    fun setCityAndCountry(city: String,country:String) {
        if(city != null && country != null){
            CityNameSingleton.setName(city)
            CountryNameSingleton.setName(country)
        }
    }
}