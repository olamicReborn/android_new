package com.maggnet.data.settings.usecase

import com.maggnet.data.settings.model.SwitchNotificationRequest
import com.maggnet.data.settings.remote.SwitchNotificationRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class SwitchNotificationUseCase @Inject constructor(
    private val switchNotificationRepository: SwitchNotificationRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, SwitchNotificationUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return switchNotificationRepository.switchNotificationVisibilitySetting(params!!.notificationListRequest)
    }

    data class Params constructor(
        val notificationListRequest: SwitchNotificationRequest
    ) {
        companion object {
            fun create(notificationListRequest: SwitchNotificationRequest) =
                Params(
                    notificationListRequest
                )
        }
    }
}
