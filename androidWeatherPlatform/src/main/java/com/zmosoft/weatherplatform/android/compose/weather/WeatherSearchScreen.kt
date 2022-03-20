package com.zmosoft.weatherplatform.android.compose.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zmosoft.weatherplatform.android.R
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse

@Composable
fun WeatherSearchScreen(
    modifier: Modifier = Modifier,
    weatherData: WeatherDataResponse? = null,
    onSearchClicked: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1.0f),
                value = searchQuery,
                label = {
                    Text(text = stringResource(id = R.string.search_location))
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        Image(
                            modifier = Modifier.clickable {
                                searchQuery = ""
                            },
                            painter = painterResource(id = R.drawable.ic_clear_black_32dp),
                            contentDescription = null
                        )
                    }
                },
                onValueChange = {
                    searchQuery = it
                }
            )
            if (searchQuery.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onSearchClicked(searchQuery)
                        },
                    painter = painterResource(id = R.drawable.ic_update_black_32dp),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Preview
@Composable
fun PreviewWeatherSearchScreen() {
    WeatherPlatformTheme {
        WeatherSearchScreen(
            onSearchClicked = {}
        )
    }
}