package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.api.APIResponse
import com.zmosoft.weatherplatform.api.ApiConfig
import com.zmosoft.weatherplatform.api.OpenWeatherService
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse
import com.zmosoft.weatherplatform.utils.BackgroundDispatcher
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val api: OpenWeatherService
) : RepositoryBase() {
    suspend fun searchWeather(
        query: String = "",
        latitude: Double? = null,
        longitude: Double? = null
    ): APIResponse<WeatherDataResponse> {
        return withContext (BackgroundDispatcher) {
            api.getCurrentWeatherDataByLocation(
                query = query,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}