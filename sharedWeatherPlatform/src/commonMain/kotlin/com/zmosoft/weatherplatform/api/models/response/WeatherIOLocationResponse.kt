package com.zmosoft.weatherplatform.api.models.response

import kotlinx.serialization.SerialName

data class WeatherIOLocationResponse(
    val count: Int? = null,
    val data: WeatherData? = null
): ResponseBase() {
    data class WeatherData(
        @SerialName("app_temp")
        val appTemp: Double? = null
    )
}

//{
//    "count": 1,
//    "data": [
//    {
//        "app_temp": 14.4,
//        "aqi": 45,
//        "city_name": "Albuquerque",
//        "clouds": 42,
//        "country_code": "US",
//        "datetime": "2022-12-07:20",
//        "dewpt": 2.9,
//        "dhi": 93.99,
//        "dni": 830.54,
//        "elev_angle": 30.41,
//        "ghi": 508.49,
//        "gust": 7.3671875,
//        "h_angle": 18,
//        "lat": 35.0844,
//        "lon": -106.6504,
//        "ob_time": "2022-12-07 20:22",
//        "pod": "d",
//        "precip": 0,
//        "pres": 845.5,
//        "rh": 44,
//        "slp": 1008.3661,
//        "snow": 0,
//        "solar_rad": 487.2,
//        "sources": [
//        "rtma"
//        ],
//        "state_code": "NM",
//        "station": "C9752",
//        "sunrise": "14:02",
//        "sunset": "23:54",
//        "temp": 14.4,
//        "timezone": "America/Denver",
//        "ts": 1670444528,
//        "uv": 2.0329967,
//        "vis": 16,
//        "weather": {
//        "icon": "c02d",
//        "description": "Scattered clouds",
//        "code": 802
//    },
//        "wind_cdir": "S",
//        "wind_cdir_full": "south",
//        "wind_dir": 178,
//        "wind_spd": 3.1009147
//    }
//    ]
//}