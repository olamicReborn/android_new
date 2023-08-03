package com.maggnet.data.settings.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class HelpIssuesListResponse(@SerializedName("data") val data: SettingsData = SettingsData()
) : BaseResponse() {

    data class SettingsData(
        @SerializedName("rows") var rows: List<RowsData>? = null
    )

    data class RowsData(
        @SerializedName("id") var id: String = String.empty,
        @SerializedName("topic") var topic: String = String.empty
    )
}