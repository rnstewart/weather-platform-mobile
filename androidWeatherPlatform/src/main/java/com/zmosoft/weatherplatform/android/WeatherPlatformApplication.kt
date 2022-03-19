package com.zmosoft.weatherplatform.android

import android.app.Application
import com.zmosoft.weatherplatform.android.di.AppModule
import com.zmosoft.weatherplatform.android.di.ApplicationComponent
import com.zmosoft.weatherplatform.android.di.DaggerApplicationComponent
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class WeatherPlatformApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build().apply {
                inject(this@WeatherPlatformApplication)
            }
    }
}