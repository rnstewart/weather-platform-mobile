package com.zmosoft.weatherplatform.android.mvvm.viewmodels

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.zmosoft.weatherplatform.android.mvvm.utils.checkLocationPermission
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val weatherRepository: WeatherRepository,
    activity: Activity
) : ViewModel() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    val weatherData: MutableState<WeatherDataResponse?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun searchWeather(query: String = "", location: Location? = null) {
        loading.value = true
        viewModelScope.launch {
            val result = weatherRepository.searchWeather(
                query = query,
                latitude = location?.latitude,
                longitude = location?.longitude
            )

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

    fun updateLocation(context: Context) {
        if (context.checkLocationPermission(both = true)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                searchWeather(
                    location = location
                )
            }
        }
    }
}