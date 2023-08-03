package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

data class HelpSettingsRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("topic_id") val topicId: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("description") val description: String
)