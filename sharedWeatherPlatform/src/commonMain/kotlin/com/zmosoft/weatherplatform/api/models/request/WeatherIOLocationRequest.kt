package com.zmosoft.weatherplatform.api.models.request

import com.zmosoft.weatherplatform.api.APIKeys
import io.ktor.client.request.*
import io.ktor.http.*

class WeatherIOLocationRequest(
    latitude: Double? = null,
    longitude: Double? = null
) : RequestBase(
    method = HttpMethod.Get,
    path = "current",
    requireAuth = false,
    queryParams = mapOf(
        "lon" to longitude.toString(),
        "lat" to latitude.toString()
    )
)