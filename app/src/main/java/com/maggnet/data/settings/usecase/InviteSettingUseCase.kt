package com.maggnet.data.settings.usecase

import com.maggnet.data.settings.model.HelpSettingResponse
import com.maggnet.data.settings.model.HelpSettingsRequest
import com.maggnet.data.settings.model.InviteSettingResponse
import com.maggnet.data.settings.model.InviteSettingsRequest
import com.maggnet.data.settings.remote.HelpSettingRepository
import com.maggnet.data.settings.remote.InviteSettingRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class InviteSettingUseCase @Inject constructor(
    private val inviteSettingRepository: InviteSettingRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<InviteSettingResponse, InviteSettingUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<InviteSettingResponse> {
        return inviteSettingRepository.inviteFriend(params!!.inviteSettingsRequest)
    }

    data class Params constructor(
        val inviteSettingsRequest: InviteSettingsRequest
    ) {
        companion object {
            fun create(inviteSettingsRequest: InviteSettingsRequest) =
                Params(
                    inviteSettingsRequest
                )
        }
    }
}