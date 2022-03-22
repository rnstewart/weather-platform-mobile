package com.zmosoft.weatherplatform.api

import kotlinx.serialization.Serializable

@Serializable
data class APIKeys(
    val openWeatherMap: OpenWeatherMap = OpenWeatherMap(),
    val googleMaps: GoogleMaps = GoogleMaps()
) {
    @Serializable
    data class OpenWeatherMap(
        val apiKey: String = "",
        val apiHost: String = ""
    )

    @Serializable
    data class GoogleMaps(
        val apiKey: String = ""
    )
}