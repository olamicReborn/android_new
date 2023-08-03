package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class TransferRewardsResponse(
    @SerializedName("data") val data: TransferRewardsData = TransferRewardsData()
) : BaseResponse() {

    data class TransferRewardsData(
        @SerializedName("order_id") val order_id: String = String.empty,
        @SerializedName("received_from_user_id") val received_from_user_id: String = String.empty,
        @SerializedName("id") val id: String = String.empty,
        @SerializedName("user_id") val user_id: String = String.empty,
        @SerializedName("business_id") val business_id: String = String.empty,
        @SerializedName("reward_points") val reward_points: String = String.empty,
        @SerializedName("type") val type: String = String.empty,
        @SerializedName("sub_type") val sub_type: String = String.empty,

        )

}