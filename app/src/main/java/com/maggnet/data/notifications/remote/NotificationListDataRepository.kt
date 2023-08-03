package com.maggnet.data.notifications.remote

import com.maggnet.data.notifications.model.DeleteNotificationRequest
import com.maggnet.data.notifications.model.NotificationListRequest
import com.maggnet.data.notifications.model.NotificationListResponse
import com.maggnet.data.notifications.service.NotificationApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class NotificationListDataRepository @Inject constructor(
    private val notificationApiServices: NotificationApiServices
) : NotificationListRepository {

    override fun getNotificationsList(notificationListRequest: NotificationListRequest): Single<NotificationListResponse> {
        return notificationApiServices.getNotificationsList(notificationListRequest)
    }

    override fun deleteNotification(deleteNotificationRequest: DeleteNotificationRequest): Single<BaseResponse> {
        return notificationApiServices.deleteNotification(deleteNotificationRequest)
    }
}