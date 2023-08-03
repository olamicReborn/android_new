package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

data class UserProfileRequest (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("profile_image") val profile_image: String,
)