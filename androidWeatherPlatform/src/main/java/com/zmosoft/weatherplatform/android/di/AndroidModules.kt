package com.zmosoft.weatherplatform.android.di

import com.zmosoft.weatherplatform.android.mvvm.viewmodels.MainActivityViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object AndroidModules {
    val vmModule = DI.Module("Android/VM") {
        bind {
            singleton {
                MainActivityViewModel(
                    weatherRepository = instance()
                )
            }
        }
    }
}