package com.maggnet.data.coupons.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty
import org.json.JSONObject

class CouponsListResponse(@SerializedName("data") val data: List<JsonObject>? = null) :
    BaseResponse() {


    data class CouponCategory(
        var searchText: String = String.empty,
        val couponList: ArrayList<CouponsListData> = arrayListOf(),
        val title: String = String.empty,
        var categoryImageUrl: String = String.empty,
        var id: String = String.empty
    )

    class CouponsListData {
        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("description")
        val description: String = String.empty

        @SerializedName("business_name")
        val businessName: String = String.empty

        @SerializedName("discount")
        val discount: String = String.empty

        @SerializedName("business_address_id")
        val businessAddressId: String = String.empty

        @SerializedName("business_id")
        val businessId: String = String.empty

        @SerializedName("category_id")
        val categoryId: String = String.empty

        @SerializedName("lat")
        val lat: String = String.empty

        @SerializedName("lng")
        val lng: String = String.empty

        @SerializedName("map_icon")
        val mapIcon: String = String.empty

        @SerializedName("qr_code")
        val qrCode: String = String.empty

        @SerializedName("shop_logo")
        val shop_logo: String = String.empty

        @SerializedName("shop_cover")
        val shop_cover: String = String.empty

        @SerializedName("about")
        val about: String = String.empty

        @SerializedName("is_in_wishlist")
        val isInWishList: Int = 0

        @SerializedName("is_in_wallet")
        val isInWallet: String = String.empty

        @SerializedName("filter_icon")
        val filterIcon: String = String.empty

        @SerializedName("distance")
        val distance: String = String.empty

        @SerializedName("expiry_date")
        val expiryDate: String = String.empty

        @SerializedName("CouponValidWeekdays")
        val couponValidWeekdays : ArrayList<CouponValidWeekdays> = arrayListOf()

    }

    class CouponValidWeekdays {
        @SerializedName("day")
        val day: String = String.empty

        @SerializedName("start_time")
        val startTime: String = String.empty

        @SerializedName("end_time")
        val endTime: String = String.empty
    }
}