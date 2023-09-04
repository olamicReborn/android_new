package com.maggnet.data.qrcode.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class QRCodeResponseByImei(@SerializedName("data") val data: String
) : BaseResponse() {


}