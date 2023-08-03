package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.InviteSettingResponse
import com.maggnet.data.settings.model.InviteSettingsRequest
import io.reactivex.Single

interface InviteSettingRepository {
    fun inviteFriend(inviteSettingsRequest: InviteSettingsRequest): Single<InviteSettingResponse>
}