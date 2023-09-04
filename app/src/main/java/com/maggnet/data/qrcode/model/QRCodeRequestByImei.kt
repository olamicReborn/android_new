package com.maggnet.data.qrcode.model

import com.google.gson.annotations.SerializedName

data class QRCodeRequestByImei
    (@SerializedName("imei") val imei: String)
