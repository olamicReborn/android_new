package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class InviteSettingResponse(@SerializedName("data") val data: InviteData = InviteData()
) : BaseResponse() {

    data class InviteData(
        @SerializedName("referral_code") val referralCode: String = String.empty,
    )
}