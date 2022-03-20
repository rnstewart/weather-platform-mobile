package com.zmosoft.weatherplatform.android.mvvm.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val weatherData: MutableState<WeatherDataResponse?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun searchWeather(query: String) {
        loading.value = true
        viewModelScope.launch {
            val result = weatherRepository.searchWeather(query = query)

            withContext(Dispatchers.Main) {
                if (result.success) {
                    weatherData.value = result.data
                } else {
                    weatherData.value = null
                }
                loading.value = false
            }
        }
    }
}