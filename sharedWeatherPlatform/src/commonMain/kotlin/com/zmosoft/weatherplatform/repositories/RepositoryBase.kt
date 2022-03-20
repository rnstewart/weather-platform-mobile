package com.zmosoft.weatherplatform.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class RepositoryBase {
    val repositoryScope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}