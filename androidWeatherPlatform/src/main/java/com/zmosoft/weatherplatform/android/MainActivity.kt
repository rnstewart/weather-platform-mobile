package com.zmosoft.weatherplatform.android

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.zmosoft.weatherplatform.android.compose.WeatherPlatformTheme
import com.zmosoft.weatherplatform.android.compose.main.MainScreen
import com.zmosoft.weatherplatform.android.di.AndroidModules
import com.zmosoft.weatherplatform.android.mvvm.utils.checkLocationPermission
import com.zmosoft.weatherplatform.android.mvvm.utils.hideSoftKeyboard
import com.zmosoft.weatherplatform.android.mvvm.utils.requestLocationPermission
import com.zmosoft.weatherplatform.android.mvvm.viewmodels.MainActivityViewModel
import com.zmosoft.weatherplatform.android.utils.Constants
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

    private val viewModel: MainActivityViewModel by di.instance(
        arg = this
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherPlatformTheme {
                MainScreen(
                    weatherData = viewModel.weatherRepository.value.data.data,
                    loading = viewModel.loading.value,
                    onSearchClicked = {
                        hideSoftKeyboard()
                        viewModel.searchLocation(it)
                    },
                    onLocationClicked = {
                        if (checkLocationPermission(both = true)) {
                            viewModel.updateLocation(context = this)
                        } else {
                            requestLocationPermission(Constants.RequestCodes.REQUEST_LOCATION_PERMISSION)
                        }
                    }
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.RequestCodes.REQUEST_LOCATION_PERMISSION -> {
                if (!grantResults.any { it == PackageManager.PERMISSION_DENIED }) {
                    viewModel.updateLocation(context = this)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
