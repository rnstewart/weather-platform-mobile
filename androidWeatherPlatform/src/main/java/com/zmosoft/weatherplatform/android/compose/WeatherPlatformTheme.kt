package com.zmosoft.weatherplatform.android.compose

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.zmosoft.weatherplatform.android.R

@Composable
fun WeatherPlatformTheme(
    materialColors: Colors = lightColors(
        primary = colorResource(id = R.color.colorPrimary),
        primaryVariant = colorResource(id = R.color.colorPrimaryDark),
        secondary = colorResource(id = R.color.colorAccent)
    ),
    materialTypography: Typography = Typography(),
    materialShapes: Shapes = Shapes(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = materialColors,
        typography = materialTypography,
        shapes = materialShapes,
        content = content
    )
}