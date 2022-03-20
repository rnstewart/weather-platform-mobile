package com.zmosoft.weatherplatform.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.android.compose.main.MainScreen
import com.zmosoft.weatherplatform.android.mvvm.utils.hideSoftKeyboard
import com.zmosoft.weatherplatform.android.mvvm.viewmodels.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as WeatherPlatformApplication).appComponent.inject(this)

        setContent {
            WeatherPlatformTheme {
                MainScreen(
                    weatherData = viewModel.weatherData.value,
                    loading = viewModel.loading.value,
                    onSearchClicked = {
                        hideSoftKeyboard()
                        viewModel.searchWeather(it)
                    }
                )
            }
        }
    }
}
