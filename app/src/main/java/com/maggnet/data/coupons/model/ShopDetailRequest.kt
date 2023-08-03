package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class ShopDetailRequest(@SerializedName("lat") val lat: Double,
                             @SerializedName("long") val long: Double)
