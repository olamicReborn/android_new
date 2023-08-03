package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.RewardsHistoryRequest
import com.maggnet.data.settings.model.RewardsHistoryResponse
import com.maggnet.data.settings.service.SettingsApiServices
import io.reactivex.Single
import javax.inject.Inject

class RewardsHistoryDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : RewardsHistoryRepository {

    override fun getRewardsHistory(rewardsHistoryRequest: RewardsHistoryRequest): Single<RewardsHistoryResponse> {
        return settingsApiServices.getRewardsHistory(rewardsHistoryRequest)
    }
}