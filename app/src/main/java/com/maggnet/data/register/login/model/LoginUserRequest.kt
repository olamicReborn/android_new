package com.maggnet.data.register.login.model

import com.google.gson.annotations.SerializedName

data class LoginUserRequest(@SerializedName("email") val email: String,
                            @SerializedName("password") val password: String)