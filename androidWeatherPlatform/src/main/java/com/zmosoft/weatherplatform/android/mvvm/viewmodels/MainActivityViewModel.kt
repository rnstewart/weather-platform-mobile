package com.zmosoft.weatherplatform.android.mvvm.viewmodels

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.zmosoft.weatherplatform.android.mvvm.utils.checkLocationPermission
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    weatherRepo: WeatherRepository,
    activity: Activity
) : ViewModel() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    val weatherRepository = mutableStateOf(weatherRepo)

    fun searchWeather(query: String = "", location: Location? = null) {
        weatherRepository.value = weatherRepository.value.isLoading(true)

        viewModelScope.launch {
            val repository = weatherRepository.value.searchWeather(
                query = query,
                latitude = location?.latitude,
                longitude = location?.longitude
            )

            withContext(Dispatchers.Main) {
                weatherRepository.value = repository
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