package com.maggnet.data.register.otp.model

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(@SerializedName("country_code") val countryCode: String,
                               @SerializedName("phone_number") val phoneNumber: String,
                               @SerializedName("email") val email: String,
                               @SerializedName("password") val password: String,
                               @SerializedName("referral_code") val referralCode: String,
                               @SerializedName("name") val name: String,
                               @SerializedName("profile_image") val profile_image: String,)
