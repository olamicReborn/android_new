package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class CouponsListRequest(@SerializedName("lat") val lat: Double,
                              @SerializedName("long") val long: Double,
                              @SerializedName("page") val page: Int,
                              @SerializedName("user_id") val userId: String,
                              @SerializedName("sortValue") val sortValue: Int,
                              @SerializedName("search") val search: String)