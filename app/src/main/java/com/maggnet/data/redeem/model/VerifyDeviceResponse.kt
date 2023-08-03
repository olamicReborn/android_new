package com.maggnet.data.redeem.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class VerifyDeviceResponse(
    @SerializedName("data") val data: Data = Data()
) : BaseResponse() {


    class Data {
        @SerializedName("is_registered")
        val isRegistered: Int = 0
    }

}