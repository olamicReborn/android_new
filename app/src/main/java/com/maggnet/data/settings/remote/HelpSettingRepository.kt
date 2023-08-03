package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.HelpIssuesListResponse
import com.maggnet.data.settings.model.HelpSettingResponse
import com.maggnet.data.settings.model.HelpSettingsRequest
import io.reactivex.Single

interface HelpSettingRepository {
    fun queryRaise(helpSettingsRequest: HelpSettingsRequest): Single<HelpSettingResponse>

    fun getIssuesList(): Single<HelpIssuesListResponse>

}