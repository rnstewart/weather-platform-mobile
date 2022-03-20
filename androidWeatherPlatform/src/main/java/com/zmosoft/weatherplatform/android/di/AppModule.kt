package com.zmosoft.weatherplatform.android.di

import com.zmosoft.weatherplatform.android.WeatherPlatformApplication
import com.zmosoft.weatherplatform.api.APIKeys
import com.zmosoft.weatherplatform.api.ApiConfig
import com.zmosoft.weatherplatform.api.OpenWeatherService
import com.zmosoft.weatherplatform.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: WeatherPlatformApplication) {

    @Provides
    @Singleton
    fun provideApiKeys(): APIKeys {
        return ApiConfig.apiKeys
    }

    @Provides
    @Singleton
    fun provideWeatherService(
        apiKeys: APIKeys
    ): OpenWeatherService {
        return OpenWeatherService(apiKeys = apiKeys)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: OpenWeatherService
    ): WeatherRepository {
        return WeatherRepository(api)
    }
}