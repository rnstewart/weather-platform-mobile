package com.zmosoft.weatherplatform.api.models.request

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

abstract class RequestBase(
    val method: HttpMethod,
    val path: String,
    val contentType: ContentType = ContentType.Application.Json,
    val pathParams: List<Any> = listOf(),
    val queryParams: Map<String, Any> = mapOf(),
    val bodyData: Any? = null,
    val expectSuccess: Boolean = true,
    val requireAuth: Boolean = true,
    private val apiVersion: String = "2.5"
) {
    val fullPath: String
        get() = "data/v$apiVersion/$path" + if (pathParams.isNotEmpty())
            "/" + pathParams.map { it.toString().trim() }.filter { it.isNotEmpty() }.joinToString("/")
        else
            ""

    val params: HttpRequestBuilder.() -> Unit = {
        queryParams.forEach { (key, value) ->
            parameter(key, value.toString())
        }
    }
}