package com.maggnet.data.notifications.model

import com.google.gson.annotations.SerializedName

data class DeleteNotificationRequest(
    @SerializedName("id") val id: String,
)