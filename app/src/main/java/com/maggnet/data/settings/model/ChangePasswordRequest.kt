package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("old_password") val oldPassword: String,
    @SerializedName("password") val password: String,
)