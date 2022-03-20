package com.zmosoft.weatherplatform.android.mvvm.utils

import com.zmosoft.weatherplatform.android.mvvm.viewmodels.MainActivityViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubcomponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubcomponent
    }

    fun mainActivityViewModel(): MainActivityViewModel
}