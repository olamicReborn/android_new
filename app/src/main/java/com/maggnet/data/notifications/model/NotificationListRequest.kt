package com.maggnet.data.notifications.model

import com.google.gson.annotations.SerializedName

data class NotificationListRequest(@SerializedName("user_id") val userId: String)
