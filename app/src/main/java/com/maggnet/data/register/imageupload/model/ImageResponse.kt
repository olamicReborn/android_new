package com.maggnet.data.register.imageupload.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class ImageResponse (@SerializedName("data") val data : String = String.empty
) : BaseResponse() {
}