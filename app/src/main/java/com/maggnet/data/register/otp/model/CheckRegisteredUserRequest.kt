package com.maggnet.data.register.otp.model

import com.google.gson.annotations.SerializedName

data class CheckRegisteredUserRequest(@SerializedName("phone_number") val phoneNumber: String,@SerializedName("country_code") val country_code: String)
