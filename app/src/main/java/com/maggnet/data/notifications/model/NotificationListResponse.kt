package com.maggnet.data.notifications.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

data class NotificationListResponse(
    @SerializedName("data") val data: NotificationsData = NotificationsData()
) : BaseResponse() {

    data class NotificationsData(
        var tittle: String = String.empty,

        @SerializedName("this_week") var thisWeek: List<Notification>? = null,

        @SerializedName("this_month")
        var thisMonth: List<Notification>? = null,

        @SerializedName("earlier")
        var earlier: List<Notification>? = null
    )

    data class Notification(
        @SerializedName("id") val id: String = String.empty,
        @SerializedName("title") val title: String = String.empty,
        @SerializedName("sub_title") val subTitle: String = String.empty,
        @SerializedName("status") val status: String = String.empty,
        @SerializedName("business_id") val businessId: String = String.empty,
        @SerializedName("createdAt") val createdAt: String = String.empty,
        @SerializedName("type") var type: String = String.empty
    )
}