package com.zmosoft.weatherplatform.android.compose.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    WeatherPlatformTheme {
        MainScreen()
    }
}