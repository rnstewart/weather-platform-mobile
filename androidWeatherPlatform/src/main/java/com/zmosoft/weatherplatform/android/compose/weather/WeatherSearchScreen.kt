package com.zmosoft.weatherplatform.android.compose.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.zmosoft.weatherplatform.android.R
import com.zmosoft.weatherplatform.android.api.models.*
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.api.models.response.geo.AutocompletePlacesData
import com.zmosoft.weatherplatform.api.models.response.weather.WeatherDataResponse

@Composable
fun WeatherSearchScreen(
    modifier: Modifier = Modifier,
    weatherData: WeatherDataResponse? = null,
    autocompleteResults: List<AutocompletePlacesData.Prediction> = listOf(),
    loading: Boolean = false,
    onSearchClicked: (String) -> Unit,
    onAutocompleteResultClicked: (AutocompletePlacesData.Prediction) -> Unit,
    onLocationClicked: () -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
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
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            } else if (searchQuery.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onSearchClicked(searchQuery)
                        },
                    painter = painterResource(id = R.drawable.ic_update_black_32dp),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onLocationClicked()
                        },
                    painter = painterResource(id = R.drawable.ic_location_black_32dp),
                    contentDescription = null
                )
            }
        }

        if (autocompleteResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth(),
                content = {
                    itemsIndexed(items = autocompleteResults) { i, prediction ->
                        if (i > 0) {
                            Divider()
                        }

                        Row(
                            modifier = Modifier
                                .clickable {
                                    onAutocompleteResultClicked(prediction)
                                }
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = prediction.description ?: "",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.weight(1.0f))
                        }
                    }
                }
            )
        } else if (weatherData != null) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = weatherData.name ?: "",
                style = MaterialTheme.typography.h4
            )

            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = weatherData.getCurrentTempStr(LocalContext.current) ?: "",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = weatherData.currentWeatherCondition ?: ""
                    )
                }
                val weatherIconUrl = weatherData.getWeatherIconUrl(LocalContext.current)
                if (weatherIconUrl?.isNotEmpty() == true) {
                    Image(
                        painter = rememberImagePainter(weatherIconUrl),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
            }

            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1.0f))
                weatherData.getWindIcon(LocalContext.current)?.let { windIcon ->
                    Image(
                        painter = painterResource(id = windIcon),
                        contentDescription = null
                    )
                }
                Text(text = weatherData.getWindStr(LocalContext.current) ?: "")
                Spacer(modifier = Modifier.weight(1.0f))
            }

            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1.0f))
                weatherData.sunriseIcon?.let { sunriseIcon ->
                    Image(
                        painter = painterResource(id = sunriseIcon),
                        contentDescription = null
                    )
                    Text(text = weatherData.sunriseStr ?: "")
                }
                Spacer(modifier = Modifier.weight(1.0f))
                weatherData.sunsetIcon?.let { sunsetIcon ->
                    Image(
                        painter = painterResource(id = sunsetIcon),
                        contentDescription = null
                    )
                    Text(text = weatherData.sunsetStr ?: "")
                }
                Spacer(modifier = Modifier.weight(1.0f))
            }
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Preview
@Composable
fun PreviewWeatherSearchScreen() {
    WeatherPlatformTheme {
        WeatherSearchScreen(
            weatherData = WeatherDataResponse(
                name = "Albuquerque",
                main = WeatherDataResponse.Main(
                    temp = 283.0
                ),
                weather = listOf(
                    WeatherDataResponse.Weather(
                        main = "Clouds"
                    )
                ),
                wind = WeatherDataResponse.Wind(
                    speed = 12.3,
                    deg = 51
                ),
                sys = WeatherDataResponse.Sys(
                    sunrise = 1647754260,
                    sunset = 1647799183
                )
            ),
            loading = false,
            onSearchClicked = {},
            onAutocompleteResultClicked = {},
            onLocationClicked = {}
        )
    }
}