package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class HelpSettingResponse(@SerializedName("data") val data: SettingsData = SettingsData()
) : BaseResponse() {

    data class SettingsData(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("subject") val subject: String = String.empty,
        @SerializedName("description") val description: String = String.empty,
        @SerializedName("topic_id") val topic_id: String = String.empty
    )
}