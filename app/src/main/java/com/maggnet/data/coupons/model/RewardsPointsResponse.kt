package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class RewardsPointsResponse(@SerializedName("data") val data: List<RewardsData>? = null) :
    BaseResponse() {


    class RewardsData {

        @SerializedName("reward_points")
        val rewardPoints: String = String.empty

        @SerializedName("Business")
        val business: Business = Business()
    }

    class Business {
        @SerializedName("name")
        val name: String = String.empty

    }
}
