package com.zmosoft.weatherplatform.api

import com.zmosoft.weatherplatform.api.models.Auth
import com.zmosoft.weatherplatform.api.models.request.RequestBase
import com.zmosoft.weatherplatform.api.models.response.ResponseBase
import com.zmosoft.weatherplatform.utils.PlatformInfo
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

open class Api (
    private val baseUrl: String,
    val auth: Auth = Auth()
) {
    var onAuthFail: () -> Unit = {}
    var onApiError: (APIResponse.APIError) -> Unit = {}

    private val jsonParser by lazy {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
    }

    fun isLoggedIn(): Boolean {
        return auth.hasAuthHeader()
    }

    protected fun logEvent(logStr: String) {
        if (PlatformInfo.isDebug()) {
            if (PlatformInfo.appReference() == "android") {
                Napier.d(logStr, tag = "HttpCalls")
            } else {
                print("HttpCalls: $logStr\n")
            }
        }
    }

    protected fun getClient(auth: Auth?, expectSuccess: Boolean = true): HttpClient {
        return HttpClient {
            this.expectSuccess = expectSuccess
            install(JsonFeature) {
                serializer = KotlinxSerializer(jsonParser)
                acceptContentTypes = listOf(
                    ContentType.Application.Json
                )
            }
            if (PlatformInfo.isDebug()) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
            defaultRequest {
                host = baseUrl
                val authHeader = auth?.authHeader
                if (authHeader?.isNotEmpty() == true) {
                    header("Authorization", "Basic $authHeader")
                }
            }
        }
    }

    protected suspend inline fun <reified T : ResponseBase> decodeResponse(response: HttpResponse): Triple<T?, HttpStatusCode, String?> {
        logEvent("decodeObject(): httpStatusCode = ${response.status}; str = ${response.readText()}")
        val responseObject = response.receive<T>()
        logEvent("decodeObject(): responseObject<${responseObject::class.simpleName}> = $responseObject")
        return Triple(
            responseObject,
            response.status,
            responseObject.message
        )
    }

    protected suspend inline fun <reified T : ResponseBase> apiCall(
        request: RequestBase
    ): APIResponse<T> {
        logEvent("apiCall(method = ${request.method}, path = \"${request.path}\", expectSuccess = ${request.expectSuccess}, requireAuth = ${request.requireAuth})")
        val result = if (request.requireAuth && !isLoggedIn()) {
            APIResponse.unauthorizedResponse()
        } else {
            try {
                val (data, httpStatusCode, message) = decodeResponse<T>(
                    getClient(
                        if (request.requireAuth)
                            auth
                        else
                            null,
                        request.expectSuccess
                    ).request {
                        url("https", host, port, request.fullPath)
                        this.method = request.method
                        contentType(request.contentType)
                        if (method != HttpMethod.Get) {
                            if (PlatformInfo.isDebug()) {
                                logEvent("path = ${method.value} ${request.path}, body = ${request.bodyData}\n")
                            }
                            request.bodyData?.let { data ->
                                this.body = data
                            }
                        }
                        apply(request.queryParams)
                        if (PlatformInfo.isDebug()) {
                            val parameters: List<String> = url.parameters.entries().flatMap {
                                it.value
                            }
                            logEvent("path = ${method.value} ${request.path}, params = ${parameters}\n")
                        }
                    }
                )
                APIResponse(
                    data = data,
                    message = if (message?.isNotEmpty() == true)
                        message
                    else
                        httpStatusCode.description,
                    statusCode = httpStatusCode
                )
            } catch (e: Exception) {
                logEvent("Exception = $e")
                APIResponse.exceptionResponse(e)
            }
        }

        val error = result.error
        if (auth.hasAuthHeader() && result.statusCode == HttpStatusCode.Unauthorized) {
            auth.clearAuthHeader()
            onAuthFail()
        } else if (error != null) {
            Napier.e("APIError: path = ${request.method.value} ${request.path}, error = $error")
            onApiError(error)
        }

        logEvent("apiCall(method = ${request.method}, path = \"${request.path}\"): statusCode = ${result.statusCode}; message = \"${result.message}\"; response = ${result.data}; result = $result")
        return result
    }
}