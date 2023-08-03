package com.maggnet.data.notifications.usecase

import com.maggnet.data.notifications.model.NotificationListRequest
import com.maggnet.data.notifications.model.NotificationListResponse
import com.maggnet.data.notifications.remote.NotificationListRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationListRepository: NotificationListRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<NotificationListResponse, NotificationUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<NotificationListResponse> {
        return notificationListRepository.getNotificationsList(params!!.notificationListRequest)
    }

    data class Params constructor(
        val notificationListRequest: NotificationListRequest
    ) {
        companion object {
            fun create(notificationListRequest: NotificationListRequest) =
                Params(
                    notificationListRequest
                )
        }
    }
}