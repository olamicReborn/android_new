package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class GetRecentListRequest(@SerializedName("user_id") val userId: String,
                                @SerializedName("page") val page: Int,
                                @SerializedName("search") val search: String,
                                @SerializedName("category_id") val category_id: String)
