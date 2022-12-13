package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.di.SharedModules
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

@Suppress("unused")
class Repositories: DIAware {
    override val di by DI.lazy {
        importAll(
            SharedModules.repositoriesModule,
            SharedModules.dataModule
        )
    }

    val weatherRepository: WeatherRepository by di.instance()
    val googleMapsRepository: GoogleMapsRepository by di.instance()
}