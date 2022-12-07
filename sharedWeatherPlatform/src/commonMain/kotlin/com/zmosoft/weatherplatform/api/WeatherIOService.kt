package com.zmosoft.weatherplatform.api

import com.zmosoft.weatherplatform.api.models.request.WeatherIOLocationRequest
import com.zmosoft.weatherplatform.api.models.response.WeatherIOLocationResponse
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse

class WeatherIOService(
    apiKeys: APIKeys
) : Api(
    baseUrl = apiKeys.openWeatherMap.apiHost
) {
    suspend fun getCurrentWeatherDataByLocation(latitude: Double? = null, longitude: Double? = null): APIResponse<WeatherIOLocationResponse> {
        return apiCall(
            WeatherIOLocationRequest(
                latitude = latitude,
                longitude = longitude
            )
        )
    }
}