package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class BannerListResponse(@SerializedName("data") val data: BannerData = BannerData()
) : BaseResponse() {


    class BannerData {
        @SerializedName("rows")
        var rows = listOf<Rows>()
    }

    class Rows {
        @SerializedName("banner_url")
        val banner_url: String = String.empty

        @SerializedName("redirect_to")
        val redirectTo: String = String.empty

        @SerializedName("external_link")
        val externalLink: String = String.empty

        @SerializedName("business_id")
        val businessId: String = String.empty
    }
}