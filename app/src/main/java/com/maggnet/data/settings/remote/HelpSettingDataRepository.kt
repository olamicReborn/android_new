package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.HelpIssuesListResponse
import com.maggnet.data.settings.model.HelpSettingResponse
import com.maggnet.data.settings.model.HelpSettingsRequest
import com.maggnet.data.settings.service.SettingsApiServices
import io.reactivex.Single
import javax.inject.Inject

class HelpSettingDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : HelpSettingRepository {
    override fun queryRaise(helpSettingsRequest: HelpSettingsRequest): Single<HelpSettingResponse> {
        return settingsApiServices.queryRaise(helpSettingsRequest)
    }

    override fun getIssuesList(): Single<HelpIssuesListResponse> {
      return settingsApiServices.getIssuesList()
    }
}