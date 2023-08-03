package com.maggnet.data.qrcode.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class ResetQRCodeResponse(@SerializedName("data") val data: QRCodeData = QRCodeData()
) : BaseResponse() {

    data class QRCodeData(
        @SerializedName("qr_code") val qrCode: String = String.empty
    )
}