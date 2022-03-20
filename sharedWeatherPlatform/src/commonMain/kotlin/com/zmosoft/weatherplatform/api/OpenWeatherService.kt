package com.zmosoft.weatherplatform.api

import com.zmosoft.weatherplatform.api.models.request.WeatherDataRequest
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse

class OpenWeatherService(
    private val apiKeys: APIKeys
) : Api(
    baseUrl = "https://${apiKeys.openWeatherMap.apiHost}"
) {
    suspend fun getCurrentWeatherDataByCity(query: String = "", latitude: Double? = null, longitude: Double? = null): APIResponse<WeatherDataResponse> {
        return apiCall(
            WeatherDataRequest(
                query = query,
                latitude = latitude,
                longitude = longitude,
                apiKeys = apiKeys
            )
        )
    }
}