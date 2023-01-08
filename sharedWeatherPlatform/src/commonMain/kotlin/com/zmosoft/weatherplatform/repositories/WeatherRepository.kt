package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.api.APIResponse
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse
import com.zmosoft.weatherplatform.utils.BackgroundDispatcher
import kotlinx.coroutines.withContext

data class WeatherRepository(
    val data: WeatherData = WeatherData(),
    val error: APIResponse.APIError? = null
) : RepositoryBase() {
    data class WeatherData(
        val data: WeatherDataResponse? = null,
        val loading: Boolean = false
    )

    fun isLoading(loading: Boolean): WeatherRepository {
        return copy(
            data = data.copy(
                loading = loading
            )
        )
    }

    suspend fun searchWeather(
        query: String = "",
        latitude: Double? = null,
        longitude: Double? = null
    ): WeatherRepository {
        return withContext (BackgroundDispatcher) {
            val response = openWeatherService.getCurrentWeatherDataByLocation(
                query = query,
                latitude = latitude,
                longitude = longitude
            )

            copy(
                data = data.copy(
                    data = response.data,
                    loading = false
                ),
                error = response.error
            )
        }
    }
}