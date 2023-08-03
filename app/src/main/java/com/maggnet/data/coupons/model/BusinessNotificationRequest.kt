package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName

data class BusinessNotificationRequest(@SerializedName("business_id") val businessId: String,
                                       @SerializedName("is_on") val isOn: String)
