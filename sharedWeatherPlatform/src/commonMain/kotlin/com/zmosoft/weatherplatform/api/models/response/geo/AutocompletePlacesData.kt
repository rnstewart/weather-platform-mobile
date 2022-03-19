package com.zmosoft.openweather.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AutocompletePlacesData {
    val status: String? = null
    val predictions: List<Prediction>? = null

    @Serializable
    class Prediction {
        @Serializable
        class MatchedSubstring {
            val length: Int? = null
            val offset: Int? = null
        }

        @Serializable
        class Term {
            val offset: Int? = null
            val value: String? = null
        }

        val description: String? = null

        @SerialName("distance_meters")
        val distanceMeters: Long? = null

        val id: String? = null

        @SerialName("matched_substrings")
        val matchedSubstrings: List<MatchedSubstring>? = null

        @SerialName("place_id")
        val placeId: String? = null

        val reference: String? = null

        val terms: List<Term>? = null

        val types: List<String>? = null
    }
}
