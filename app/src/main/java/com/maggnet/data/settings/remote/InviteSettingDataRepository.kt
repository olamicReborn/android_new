package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.*
import com.maggnet.data.settings.service.SettingsApiServices
import io.reactivex.Single
import javax.inject.Inject

class InviteSettingDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : InviteSettingRepository {

    override fun inviteFriend(inviteSettingsRequest: InviteSettingsRequest): Single<InviteSettingResponse> {
        return settingsApiServices.inviteFriend(inviteSettingsRequest)

    }
}