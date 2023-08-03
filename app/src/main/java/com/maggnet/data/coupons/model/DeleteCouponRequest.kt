package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class DeleteCouponRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("coupon_id") val couponId: String,
)