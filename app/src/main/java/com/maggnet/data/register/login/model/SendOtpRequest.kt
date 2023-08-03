package com.maggnet.data.register.login.model

import com.google.gson.annotations.SerializedName

data class SendOtpRequest(@SerializedName("country_code") val country_code: String,
                          @SerializedName("phone_number") val phone_number: String)