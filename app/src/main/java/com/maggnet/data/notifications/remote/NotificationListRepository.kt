package com.maggnet.data.notifications.remote

import com.maggnet.data.notifications.model.DeleteNotificationRequest
import com.maggnet.data.notifications.model.NotificationListRequest
import com.maggnet.data.notifications.model.NotificationListResponse
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface NotificationListRepository {

    fun getNotificationsList(notificationListRequest: NotificationListRequest): Single<NotificationListResponse>

    fun deleteNotification(deleteNotificationRequest: DeleteNotificationRequest): Single<BaseResponse>

}