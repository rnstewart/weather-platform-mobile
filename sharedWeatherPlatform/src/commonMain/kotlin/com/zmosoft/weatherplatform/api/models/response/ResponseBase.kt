package com.zmosoft.weatherplatform.api.models.response

import kotlinx.serialization.Serializable

@Serializable
open class ResponseBase(
    var success: Boolean = false,
    val message: String? = null,
    val errors: List<String>? = null
) {
    override fun toString(): String {
        return "ResponseBase(success = $success, message = \"$message\", errors=\"$errors\")"
    }
}