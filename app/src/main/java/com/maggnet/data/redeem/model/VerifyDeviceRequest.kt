package com.maggnet.data.redeem.model

import com.google.gson.annotations.SerializedName

data class VerifyDeviceRequest(@SerializedName("imei_number") val imeiNumber: String)