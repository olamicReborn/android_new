package com.maggnet.data.register.login.model

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(@SerializedName("country_code") val country_code: String,
                            @SerializedName("phone_number") val phone_number: String,
                            @SerializedName("otp") val otp: String)