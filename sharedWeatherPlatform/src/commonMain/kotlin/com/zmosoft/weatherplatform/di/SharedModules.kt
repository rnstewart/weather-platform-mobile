package com.zmosoft.weatherplatform.di

import com.zmosoft.weatherplatform.api.ApiConfig
import com.zmosoft.weatherplatform.api.OpenWeatherService
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object SharedModules {
    val dataModule = DI.Module("Shared/Data") {
        bind {
            singleton {
                ApiConfig.apiKeys
            }
        }
        bind {
            singleton {
                OpenWeatherService(
                    apiKeys = instance()
                )
            }
        }
    }
    val repositoriesModule = DI.Module("Shared/Repositories") {
        bind {
            singleton {
                WeatherRepository(
                    api = instance()
                )
            }
        }
    }
}