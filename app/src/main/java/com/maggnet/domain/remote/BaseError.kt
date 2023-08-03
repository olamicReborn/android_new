package com.maggnet.domain.remote

import com.google.gson.annotations.SerializedName
import com.maggnet.ui.extensions.empty


data class BaseError(
    @SerializedName("message")
    val errorMessage: String = String.empty,
    val errorCode: ResponseErrors = ResponseErrors.RESPONSE_ERROR,
    val errorBody: String = String.empty
) : BaseResponse()

enum class ResponseErrors {
    HTTP_UNAUTHORIZED,
    HTTP_TOO_MANY_REQUEST,
    HTTP_BAD_REQUEST,
    HTTP_NOT_FOUND,
    CONNECTIVITY_EXCEPTION,
    HTTP_UNAVAILABLE,
    RESPONSE_ERROR,
    UNKNOWN_EXCEPTION,
    INTERNAL_SERVER_ERROR
}