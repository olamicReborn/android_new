package com.maggnet.domain.remote

import com.google.gson.annotations.SerializedName
import com.maggnet.ui.extensions.empty

open class BaseResponse {
    @SerializedName("success")
    val success: Int = 0

    @SerializedName("message")
    var message: String = String.empty
}