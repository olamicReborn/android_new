package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

data class InviteSettingsRequest(
    @SerializedName("user_id") val userId: String,
)