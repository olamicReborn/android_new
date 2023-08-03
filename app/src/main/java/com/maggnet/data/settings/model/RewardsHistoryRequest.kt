package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

class RewardsHistoryRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("business_id") val business_id: String,
    @SerializedName("is_for_settings") val is_for_settings: Int
)