package com.maggnet.data.qrcode.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class ScanQRCodeResponseByImei(@SerializedName("data") val data: ScanQRCodeData = ScanQRCodeData()
) : BaseResponse() {

    data class ScanQRCodeData(
        @SerializedName("user_id") val userid: String = String.empty,
        @SerializedName("id") val id: String = String.empty
    )

}