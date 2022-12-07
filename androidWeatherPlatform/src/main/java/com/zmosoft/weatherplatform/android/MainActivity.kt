package com.zmosoft.weatherplatform.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.android.compose.main.MainScreen
import com.zmosoft.weatherplatform.android.di.AndroidModules
import com.zmosoft.weatherplatform.android.mvvm.utils.hideSoftKeyboard
import com.zmosoft.weatherplatform.android.mvvm.viewmodels.MainActivityViewModel
import com.zmosoft.weatherplatform.di.SharedModules
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MainActivity : AppCompatActivity(), DIAware {
    override val di by DI.lazy {
        importAll(
            AndroidModules.vmModule,
            SharedModules.dataModule,
            SharedModules.repositoriesModule
        )
    }

    private val viewModel: MainActivityViewModel by di.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
