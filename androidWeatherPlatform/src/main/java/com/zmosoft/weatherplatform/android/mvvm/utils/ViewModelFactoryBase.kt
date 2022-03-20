package com.zmosoft.weatherplatform.android.mvvm.utils

import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Singleton


@Singleton
open class ViewModelFactoryBase(
    components: Map<Class<*>, () -> ViewModel>
) : ViewModelProvider.Factory {
    private var creators: ArrayMap<Class<*>, () -> ViewModel>? = null

    init {
        creators = ArrayMap<Class<*>, () -> ViewModel>().apply {
            components.forEach { (k, v) ->
                this[k] = v
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creators?.get(modelClass)?.let { creator ->
            creator() as T
        } ?: kotlin.run {
            (creators?.entries?.find {
                modelClass.isAssignableFrom(it.key)
            }?.value as? T) ?: kotlin.run {
                throw IllegalArgumentException("Unknown model class $modelClass")
            }
        }
    }
}