package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName

data class TransferRewardsRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("business_id") val businessId: String,
    @SerializedName("reward_points") val reward_points: String,
    @SerializedName("transfer_to") val transfer_to: String

)
