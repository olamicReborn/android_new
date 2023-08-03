package com.maggnet.data.settings.usecase

import com.maggnet.data.settings.model.HelpSettingResponse
import com.maggnet.data.settings.model.HelpSettingsRequest
import com.maggnet.data.settings.remote.HelpSettingRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class HelpSettingsUseCase @Inject constructor(
    private val helpSettingRepository: HelpSettingRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<HelpSettingResponse, HelpSettingsUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<HelpSettingResponse> {
        return helpSettingRepository.queryRaise(params!!.helpSettingsRequest)
    }

    data class Params constructor(
        val helpSettingsRequest: HelpSettingsRequest
    ) {
        companion object {
            fun create(helpSettingsRequest: HelpSettingsRequest) =
                Params(
                    helpSettingsRequest
                )
        }
    }
}