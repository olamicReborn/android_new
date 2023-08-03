package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class DeleteWishListRequest(
    @SerializedName("id") val id: String,
)