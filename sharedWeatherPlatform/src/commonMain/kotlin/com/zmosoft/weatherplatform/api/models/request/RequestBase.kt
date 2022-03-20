package com.zmosoft.weatherplatform.api.models.request

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

abstract class RequestBase(
    val method: HttpMethod,
    val path: String,
    val contentType: ContentType = ContentType.Application.Json,
    val queryParams: HttpRequestBuilder.() -> Unit = {},
    val bodyData: Any? = null,
    val expectSuccess: Boolean = true,
    val requireAuth: Boolean = true,
    private val apiVersion: Int = 1
) {
    val fullPath: String
        get() = "data/$apiVersion/$path"
}