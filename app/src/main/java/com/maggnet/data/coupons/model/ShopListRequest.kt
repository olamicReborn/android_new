package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class ShopListRequest(@SerializedName("page") val page: Int,
                           @SerializedName("user_id") val userId: String,
                           @SerializedName("category_id") val categoryId: String,
                           @SerializedName("search") val search: String)