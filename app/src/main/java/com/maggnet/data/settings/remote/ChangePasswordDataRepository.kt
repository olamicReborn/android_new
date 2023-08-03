package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.ChangePasswordRequest
import com.maggnet.data.settings.model.HelpIssuesListResponse
import com.maggnet.data.settings.model.HelpSettingResponse
import com.maggnet.data.settings.model.HelpSettingsRequest
import com.maggnet.data.settings.service.SettingsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class ChangePasswordDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : ChangePasswordRepository {

    override fun changePassword(changePasswordRequest: ChangePasswordRequest): Single<BaseResponse> {
        return settingsApiServices.changePassword(changePasswordRequest)
    }
}