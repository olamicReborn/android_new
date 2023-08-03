package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class RewardsHistoryResponse(
    @SerializedName("data") val data: RewardsHistoryData = RewardsHistoryData()
) : BaseResponse() {

    data class RewardsHistoryData(
        @SerializedName("available") val available: String = String.empty,
        @SerializedName("reward_history") var reward_history: List<RewardsData>? = null
    )

    data class RewardsData(
        @SerializedName("id") val id: String = String.empty,
        @SerializedName("type") val type: String = String.empty,
        @SerializedName("title") val title: String = String.empty,
        @SerializedName("business_id") val businessId: String = String.empty,
        @SerializedName("reward_points") val reward_points: String = String.empty
    )
}