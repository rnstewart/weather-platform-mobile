package com.zmosoft.weatherplatform.api.models.request

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

abstract class RequestBase {
    abstract val method: HttpMethod
    abstract val path: String
    open val contentType: ContentType = ContentType.Application.Json
    open val params: HttpRequestBuilder.() -> Unit = {}
    open val bodyData: Any? = null
    open val expectSuccess: Boolean = true
    open val requireAuth: Boolean = true
    open val apiVersion: Int = 1

    val fullPath: String
        get() = "api/v$apiVersion/$path"
}