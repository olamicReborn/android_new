package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class CouponsListImeiRequest(@SerializedName("imei") val imei: String,@SerializedName("user_id") val userid: String)