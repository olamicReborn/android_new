package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class AddToCartRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("coupon_id") val couponId: String,
    @SerializedName("business_address_id") val businessAddressId: String,
    @SerializedName("business_id") val businessId: String
)