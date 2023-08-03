package com.maggnet.data.register.login.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(@SerializedName("email") val email: String)