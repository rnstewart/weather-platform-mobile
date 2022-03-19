package com.zmosoft.openweather.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Geometry {
    val location: Location? = null

    @SerialName("location_type")
    val locationType: String? = null

    @Serializable
    class Location {
        val lat: Double? = null
        @SerialName("lng")
        val long: Double? = null
    }

    @Serializable
    class Viewport {
        val northeast: Location? = null
        val southwest: Location? = null
    }
}
