package com.maggnet.data.notifications.service

import com.maggnet.data.notifications.model.DeleteNotificationRequest
import com.maggnet.data.notifications.model.NotificationListRequest
import com.maggnet.data.notifications.model.NotificationListResponse
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface NotificationApiServices {
    @POST("api/user/get-all-notifications")
    fun getNotificationsList(@Body notificationListRequest: NotificationListRequest): Single<NotificationListResponse>


    @PUT("api/user/notification/remove")
    fun deleteNotification(@Body deletenNotificationRequest : DeleteNotificationRequest):  Single<BaseResponse>
}