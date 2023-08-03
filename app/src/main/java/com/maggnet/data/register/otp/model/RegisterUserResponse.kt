package com.maggnet.data.register.otp.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class RegisterUserResponse(
    @SerializedName("data") val data: UserData = UserData()
) : BaseResponse()


data class UserData(
    @SerializedName("is_registered") val isRegistered: Int = 0,
    @SerializedName("id") val userId: String = String.empty,
    @SerializedName("fname") val firstName: String = String.empty,
    @SerializedName("lname") val lastName: String = String.empty,
    @SerializedName("name") val name: String = String.empty,
    @SerializedName("email") val email: String = String.empty,
    @SerializedName("profile") val profile: String = String.empty,
    @SerializedName("phone") val phoneNumber: String = String.empty,
    @SerializedName("jwt") val token: String = String.empty,
    @SerializedName("qr_code") val qrCode: String = String.empty,
    @SerializedName("referral_discount") val referralDiscount: String = String.empty
)