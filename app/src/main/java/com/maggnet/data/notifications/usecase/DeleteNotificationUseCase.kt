package com.maggnet.data.notifications.usecase


import com.maggnet.data.notifications.model.DeleteNotificationRequest
import com.maggnet.data.notifications.remote.NotificationListRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class DeleteNotificationUseCase  @Inject constructor(
    private val notificationListRepository: NotificationListRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, DeleteNotificationUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return notificationListRepository.deleteNotification(params!!.deletenNotificationRequest)
    }

    data class Params constructor(
        val deletenNotificationRequest: DeleteNotificationRequest
    ) {
        companion object {
            fun create(deletenNotificationRequest: DeleteNotificationRequest) =
                Params(
                    deletenNotificationRequest
                )
        }
    }
}