package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.SwitchNotificationRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface SwitchNotificationRepository {
    fun switchNotificationVisibilitySetting(isEnable: SwitchNotificationRequest): Single<BaseResponse>
}