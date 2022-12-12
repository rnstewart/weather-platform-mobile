package com.zmosoft.weatherplatform.android.compose.main

import android.location.Location
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.android.compose.weather.WeatherSearchScreen
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    weatherData: WeatherDataResponse? = null,
    loading: Boolean = false,
    onSearchClicked: (String) -> Unit,
    onLocationClicked: () -> Unit
) {
    Scaffold(modifier = modifier) {
        WeatherSearchScreen(
            weatherData = weatherData,
            loading = loading,
            onSearchClicked = onSearchClicked,
            onLocationClicked = onLocationClicked
        )
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    WeatherPlatformTheme {
        MainScreen(
            onSearchClicked = {},
            onLocationClicked = {}
        )
    }
}