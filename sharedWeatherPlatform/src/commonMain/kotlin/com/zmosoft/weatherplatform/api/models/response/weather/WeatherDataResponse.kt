package com.zmosoft.weatherplatform.api.models.response.weather

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.zmosoft.weatherplatform.api.models.response.ResponseBase
import com.zmosoft.weatherplatform.utils.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.roundToInt

@Serializable
data class WeatherDataResponse(
    var id: Long? = null,
    var name: String? = null,
    var coord: Coord? = null,
    var weather: List<Weather>? = null,
    var sys: Sys? = null,
    var base: String? = null,
    var main: Main? = null,
    var visibility: Double? = null,
    var wind: Wind? = null,
    var rain: Rain? = null,
    var dt: Long? = null,
    var timeZone: Long? = null
) : ResponseBase() {
    @Transient
    private val timeFormat = DateFormat(Constants.TIME_FORMAT)

    val timeZoneHours: Int?
        get() = timeZone?.toDouble()?.let {
            it / (3600.0)
        }?.roundToInt()

    @Serializable
    data class Coord(
        var lon: Double? = null,
        var lat: Double? = null
    )

    @Serializable
    data class Weather(
        var id: Long? = null,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null
    )

    @Serializable
    data class Main(
        var temp: Double? = null,
        var pressure: Double? = null,
        var humidity: Double? = null,
        @SerialName("temp_min")
        var tempMin: Double? = null,
        @SerialName("temp_max")
        var tempMax: Double? = null
    )

    @Serializable
    data class Wind(
        var speed: Double? = null,
        var deg: Int? = null
    )

    @Serializable
    data class Rain(
        @SerialName("1h")
        var oneH: Double? = null
    )

    @Serializable
    data class Clouds(
        var all: Double? = null
    )

    @Serializable
    data class Sys(
        var type: Int? = null,
        var id: Long? = null,
        var country: String? = null,
        var sunrise: Long? = null,
        var sunset: Long? = null
    )


    val currentWeatherCondition: String?
        get() = weather?.getOrNull(0)?.main

    val sunriseStr: String?
        get() = sys?.sunrise?.let {
        DateTimeTz.fromUnix(it * 1000).format(timeFormat)
    } ?: run {
        null
    }

    val sunsetStr: String?
        get() = sys?.sunset?.let { sunset ->
            DateTimeTz.fromUnix(sunset * 1000).format(timeFormat)
        } ?: run {
            null
        }

    fun getWindDirectionString(deg: Int?): String {
        return deg?.toDouble()?.let {
            when {
                (deg >= 22.5 && deg < 67.5) -> "NE"
                (deg >= 67.5 && deg < 112.5) -> "E"
                (deg >= 112.5 && deg < 157.5) -> "SE"
                (deg >= 157.5 && deg < 202.5) -> "S"
                (deg >= 202.5 && deg < 247.5) -> "SW"
                (deg >= 247.5 && deg < 292.5) -> "W"
                (deg >= 292.5 && deg < 337.5) -> "W"
                else -> "N"
            }
        } ?: ""
    }

    companion object {
        const val ICON_URL_BASE = "https://openweathermap.org/img/wn/"
    }
}