package com.zmosoft.weatherplatform.api.models.request

import com.zmosoft.weatherplatform.api.APIKeys
import io.ktor.client.request.*
import io.ktor.http.*

class WeatherDataRequest(
    private val query: String = "",
    private val latitude: Double? = null,
    private val longitude: Double? = null,
    private val apiKeys: APIKeys
) : RequestBase(
    method = HttpMethod.Get,
    path = "weather",
    requireAuth = false,
    queryParams = {
        if (query.isNotEmpty()) {
            parameter("q", query)
        } else if (latitude != null && longitude != null) {
            parameter("lat", latitude.toString())
            parameter("lon", longitude.toString())
        }
        parameter("appid", apiKeys.openWeatherMap.apiKey)
    }
)