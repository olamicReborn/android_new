package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.SwitchNotificationRequest
import com.maggnet.data.settings.service.SettingsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class SwitchNotificationDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : SwitchNotificationRepository {

    override fun switchNotificationVisibilitySetting(isEnable: SwitchNotificationRequest): Single<BaseResponse> {
        return settingsApiServices.switchNotificationVisibility( isEnable)
    }
}