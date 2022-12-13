package com.zmosoft.weatherplatform.android.mvvm.viewmodels

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.zmosoft.weatherplatform.android.mvvm.utils.checkLocationPermission
import com.zmosoft.weatherplatform.api.models.response.geo.AutocompletePlacesData
import com.zmosoft.weatherplatform.repositories.GoogleMapsRepository
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    weatherRepo: WeatherRepository,
    googleMapsRepo: GoogleMapsRepository,
    activity: Activity
) : ViewModel() {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    val weatherRepository = mutableStateOf(weatherRepo)
    val googleMapsRepository = mutableStateOf(googleMapsRepo)
    val loading = mutableStateOf(false)

    fun searchLocation(query: String) {
        loading.value = true

        viewModelScope.launch {
            val repository = googleMapsRepository.value.placesAutoComplete(
                input = query
            )

            withContext(Dispatchers.Main) {
                googleMapsRepository.value = repository
                loading.value = false
            }
        }
    }

    fun autocompleteResultSelected(location: AutocompletePlacesData.Prediction) {
        loading.value = true

        viewModelScope.launch {
            val repository = googleMapsRepository.value.autocompleteResultSelected(
                location = location,
                weatherRepository = weatherRepository.value
            )
            withContext(Dispatchers.Main) {
                weatherRepository.value = repository
                googleMapsRepository.value = googleMapsRepository.value.clear()
            }
        }
    }

    private fun searchWeather(latitude: Double, longitude: Double) {
        loading.value = true

        viewModelScope.launch {
            val repository = weatherRepository.value.searchWeather(
                latitude = latitude,
                longitude = longitude
            )

            withContext(Dispatchers.Main) {
                weatherRepository.value = repository
                googleMapsRepository.value = googleMapsRepository.value.clear()
                loading.value = false
            }
        }
    }

    fun updateLocation(context: Context) {
        if (context.checkLocationPermission(both = true)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                searchWeather(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }
    }
}