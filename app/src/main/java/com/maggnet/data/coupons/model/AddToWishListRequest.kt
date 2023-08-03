package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class AddToWishListRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("business_id") val businessId: String,
    @SerializedName("coupon_id") val couponId: String
)