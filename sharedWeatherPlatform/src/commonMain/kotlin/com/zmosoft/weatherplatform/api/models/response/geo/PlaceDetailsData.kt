package com.zmosoft.openweather.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlaceDetailsData {
    val result: Result? = null

    @Serializable
    class Result {
        @SerialName("address_components")
        val addressComponents: List<AddressComponent>? = null

        val geometry: Geometry? = null
    }
}
