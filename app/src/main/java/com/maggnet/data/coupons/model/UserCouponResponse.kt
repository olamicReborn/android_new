package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class UserCouponResponse(@SerializedName("data") var data : CouponData  = CouponData()) : BaseResponse()  {

    class CouponData {
        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("end_date")
        val endDate: String = String.empty

        @SerializedName("description")
        val description: String = String.empty

        @SerializedName("Business")
        val business: Business = Business()
    }

    class Business {
        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("shop_logo")
        val shopLogo: String = String.empty

        @SerializedName("shop_cover")
        val shopCover: String = String.empty
    }
}