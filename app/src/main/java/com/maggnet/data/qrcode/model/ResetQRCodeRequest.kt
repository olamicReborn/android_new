package com.maggnet.data.qrcode.model

import com.google.gson.annotations.SerializedName

data class ResetQRCodeRequest(@SerializedName("user_id") val userId: String)
