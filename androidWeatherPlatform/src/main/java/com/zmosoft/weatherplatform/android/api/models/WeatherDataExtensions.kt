package com.zmosoft.weatherplatform.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.zmosoft.weatherplatform.android.R
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherData
import kotlin.math.roundToInt

fun WeatherData.getSunriseIcon(context: Context): Drawable? {
    return if (getSunriseStr()?.isNotEmpty() == true)
        ContextCompat.getDrawable(context, R.drawable.ic_sunrise_32dp)
    else
        null
}

fun WeatherData.getSunsetIcon(context: Context): Drawable? {
    return if (getSunsetStr()?.isNotEmpty() == true)
        ContextCompat.getDrawable(context, R.drawable.ic_sunset_32dp)
    else
        null
}

fun WeatherData.getWindStr(context: Context): String? {
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

fun WeatherData.getCurrentTempStr(context: Context?): String? {
    return context?.let {
        main?.temp?.kelvinToFahrenheit()?.roundToInt()?.let {
            context.getString(
                R.string.temperature_x,
                it
            )
        }
    }
}

fun WeatherData.getWindIcon(context: Context): Drawable? {
    return if (getWindStr(context)?.isNotEmpty() == true)
        ContextCompat.getDrawable(context, R.drawable.ic_wind_32dp)
    else
        null
}

fun WeatherData.getWeatherIconUrl(context: Context): String? {
    val icon = weather?.getOrNull(0)?.icon
    return if (icon?.isNotEmpty() == true) {
        val density = context.getString(R.string.icon_factor)
        "${WeatherData.ICON_URL_BASE}$icon@${density}x.png"
    } else {
        null
    }
}

