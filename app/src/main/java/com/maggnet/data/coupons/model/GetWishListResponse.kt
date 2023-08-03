package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class GetWishListResponse(@SerializedName("data") val data : List<WishListData> ? = null) : BaseResponse() {


    class WishListData {
        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("business_name")
        val businessName: String = String.empty

        @SerializedName("business_id")
        val businessId: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("shop_logo")
        val shopLogo: String = String.empty

        @SerializedName("shop_cover")
        val shopCover: String = String.empty

        @SerializedName("category_title")
        val categoryTitle: String = String.empty
    }
}