package com.maggnet.data.register.fcm.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class FCMTokenResponse(
    @SerializedName("data") val data: Data = Data()
) : BaseResponse()


data class Data(
    @SerializedName("id") val userId: String = String.empty,
    @SerializedName("user_id") val firstName: String = String.empty,
)