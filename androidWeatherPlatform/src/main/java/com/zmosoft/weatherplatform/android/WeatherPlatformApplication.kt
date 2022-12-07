package com.zmosoft.weatherplatform.android

import android.app.Application
import com.zmosoft.weatherplatform.android.di.AndroidModules
import com.zmosoft.weatherplatform.di.SharedModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.kodein.di.DI
import org.kodein.di.DIAware

class WeatherPlatformApplication : Application(), DIAware {
    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
    }

    override val di by DI.lazy {
        importAll(
            AndroidModules.vmModule,
            SharedModules.dataModule,
            SharedModules.repositoriesModule
        )
    }
}