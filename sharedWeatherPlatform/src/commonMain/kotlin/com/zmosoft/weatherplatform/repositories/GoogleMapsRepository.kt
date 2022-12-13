package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.api.models.response.geo.AutocompletePlacesData
import com.zmosoft.weatherplatform.api.APIResponse
import com.zmosoft.weatherplatform.api.GoogleMapsService

data class GoogleMapsRepository(
    private val api: GoogleMapsService,
    val data: GoogleMapsData = GoogleMapsData(),
    val error: APIResponse.APIError? = null
): RepositoryBase() {
    data class GoogleMapsData(
        val autocompletePredictions: List<AutocompletePlacesData.Prediction> = listOf(),
        val loading: Boolean = false
    )

    fun isLoading(loading: Boolean): GoogleMapsRepository {
        return copy(
            data = data.copy(
                loading = loading
            )
        )
    }

    suspend fun placesAutoComplete(
        input: String,
        latitude: Double? = null,
        longitude: Double? = null
    ): GoogleMapsRepository {
        val response = api.placesAutoComplete(
            input = input,
            latitude = latitude,
            longitude = longitude
        )

        return copy(
            data = data.copy(
                autocompletePredictions = response.data?.predictions ?: listOf(),
                loading = false
            ),
            error = response.error
        )
    }
}
