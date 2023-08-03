package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class GetWishListRequest (
    @SerializedName("user_id") val userId: String,
    @SerializedName("type") val type: String,
    @SerializedName("page") val page: String
)