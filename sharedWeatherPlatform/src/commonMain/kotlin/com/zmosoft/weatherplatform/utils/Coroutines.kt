package com.zmosoft.weatherplatform.utils

import kotlinx.coroutines.CoroutineDispatcher

expect val MainDispatcher: CoroutineDispatcher

expect val BackgroundDispatcher: CoroutineDispatcher