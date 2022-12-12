package com.zmosoft.weatherplatform.repositories

import com.zmosoft.weatherplatform.di.SharedModules
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

@Suppress("unused")
class Repositories: DIAware {
    override val di by DI.lazy {
        importAll(
            SharedModules.repositoriesModule
        )
    }

    val weatherRepository: WeatherRepository by di.instance()
}