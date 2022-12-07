package com.zmosoft.weatherplatform.android.api.models

import android.content.Context
import com.zmosoft.weatherplatform.android.R
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse
import com.zmosoft.weatherplatform.utils.kelvinToFahrenheit
import kotlin.math.roundToInt

val WeatherDataResponse.sunriseIcon: Int?
    get() = if (sunriseStr?.isNotEmpty() == true)
        R.drawable.ic_sunrise_32dp
    else
        null

val WeatherDataResponse.sunsetIcon: Int?
    get() = if (sunsetStr?.isNotEmpty() == true)
        R.drawable.ic_sunset_32dp
    else
        null

fun WeatherDataResponse.getWindStr(context: Context): String? {
    return wind?.let {
        val dir = getWindDirectionString(it.deg)
        if (dir.isNotEmpty())
            context.getString(
                R.string.wind_info_direction,
                dir,
                it.speed
            )
        else
            context.getString(
                R.string.wind_info,
                it.speed
            )
    }
}

fun WeatherDataResponse.getCurrentTempStr(context: Context?): String? {
    return context?.let {
        main?.temp?.kelvinToFahrenheit()?.roundToInt()?.let {
            context.getString(
                R.string.temperature_x,
                it
            )
        }
    }
}

fun WeatherDataResponse.getWindIcon(context: Context): Int? {
    return if (getWindStr(context)?.isNotEmpty() == true)
        R.drawable.ic_wind_32dp
    else
        null
}

fun WeatherDataResponse.getWeatherIconUrl(context: Context): String? {
    val icon = weather?.getOrNull(0)?.icon
    return if (icon?.isNotEmpty() == true) {
        val density = context.getString(R.string.icon_factor)
        "${WeatherDataResponse.ICON_URL_BASE}$icon@${density}x.png"
    } else {
        null
    }
}

