package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class GetCartListRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("page") val page: Int,
    @SerializedName("search") val search: String,
    @SerializedName("type") val type: Int,
    @SerializedName("category_id") val categoryId: String
)