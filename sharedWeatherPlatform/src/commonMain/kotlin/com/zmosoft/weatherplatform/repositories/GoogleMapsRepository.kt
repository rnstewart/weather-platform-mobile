package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.api.APIResponse
import com.zmosoft.weatherplatform.api.GoogleMapsService
import com.zmosoft.weatherplatform.api.models.response.geo.AutocompletePlacesData
import com.zmosoft.weatherplatform.api.models.response.geo.PlaceDetailsResponse
import com.zmosoft.weatherplatform.utils.BackgroundDispatcher
import kotlinx.coroutines.withContext

data class GoogleMapsRepository(
    private val api: GoogleMapsService,
    val data: GoogleMapsData = GoogleMapsData(),
    val error: APIResponse.APIError? = null
): RepositoryBase() {
    data class GoogleMapsData(
        val autocompletePredictions: List<AutocompletePlacesData.Prediction> = listOf(),
        val placeDetails: PlaceDetailsResponse.Result? = null,
        val loading: Boolean = false
    )

    fun isLoading(loading: Boolean): GoogleMapsRepository {
        return copy(
            data = data.copy(
                loading = loading
            )
        )
    }

    fun clear(): GoogleMapsRepository {
        return copy(
            data = GoogleMapsData()
        )
    }

    suspend fun placesAutoComplete(
        input: String,
        latitude: Double? = null,
        longitude: Double? = null
    ): GoogleMapsRepository {
        return withContext (BackgroundDispatcher) {
            val response = api.placesAutoComplete(
                input = input,
                latitude = latitude,
                longitude = longitude
            )

            copy(
                data = data.copy(
                    autocompletePredictions = response.data?.predictions ?: listOf(),
                    loading = false
                ),
                error = response.error
            )
        }
    }

    suspend fun autocompleteResultSelected(
        location: AutocompletePlacesData.Prediction,
        weatherRepository: WeatherRepository
    ): WeatherRepository {
        return withContext (BackgroundDispatcher) {
            val placeId = location.placeId
            if (placeId?.isNotEmpty() == true) {
                val response = api.placeDetails(placeId = placeId)
                response.data?.result?.geometry?.location?.let { locationResult ->
                    val latitude = locationResult.latitude
                    val longitude = locationResult.longitude
                    if (latitude != null && longitude != null) {
                        weatherRepository.searchWeather(
                            latitude = latitude,
                            longitude = longitude
                        )
                    } else {
                        null
                    }
                } ?: weatherRepository.copy(
                    error = response.error
                )
            } else {
                weatherRepository
            }
        }
    }

    suspend fun placeDetails(
        placeId: String,
        fields: String? = "address_component,name,geometry"
    ): GoogleMapsRepository {
        return withContext (BackgroundDispatcher) {
            val response = api.placeDetails(placeId = placeId, fields = fields)

            copy(
                data = data.copy(
                    placeDetails = response.data?.result,
                    loading = false
                ),
                error = error
            )
        }
    }
}
