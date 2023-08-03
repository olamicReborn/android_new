package com.maggnet.data.register.otp.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class CheckRegisteredUserResponse (
    @SerializedName("data") val data: Data = Data()
) : BaseResponse()


data class Data(
    @SerializedName("id") val userId: String = String.empty,
    @SerializedName("fname") val firstName: String = String.empty,
    @SerializedName("lname") val lastName: String = String.empty,
    @SerializedName("name") val name: String = String.empty,
    @SerializedName("email") val email: String = String.empty,
    @SerializedName("phone") val  phoneNumber: String = String.empty,
    @SerializedName("phone_number") val  phone_number: String = String.empty,
    @SerializedName("jwt") val  token: String = String.empty,
    @SerializedName("qr_code") val  qrCode: String = String.empty,
    @SerializedName("profile") val profile: String = String.empty,
    )