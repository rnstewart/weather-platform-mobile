package com.zmosoft.weatherplatform.api

import kotlinx.serialization.Serializable

@Serializable
class APIKeys {
    val openWeatherMap: OpenWeatherMap = OpenWeatherMap()
    val googleMaps: GoogleMaps = GoogleMaps()

    @Serializable
    class OpenWeatherMap {
        val apiKey = ""
        val apiHost = ""
    }

    @Serializable
    class GoogleMaps {
        val apiKey = ""
    }
}