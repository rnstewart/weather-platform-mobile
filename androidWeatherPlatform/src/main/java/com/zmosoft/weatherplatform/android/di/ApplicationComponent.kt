package com.zmosoft.weatherplatform.android.di

import com.zmosoft.weatherplatform.android.MainActivity
import com.zmosoft.weatherplatform.android.WeatherPlatformApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface ApplicationComponent {
    fun inject(application: WeatherPlatformApplication)
    fun inject(activity: MainActivity)
}