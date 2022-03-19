package com.zmosoft.openweather.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AddressComponent {
    @SerialName("long_name")
    val longName: String? = null

    @SerialName("short_name")
    val shortName: String? = null

    val types: List<String>? = null
}