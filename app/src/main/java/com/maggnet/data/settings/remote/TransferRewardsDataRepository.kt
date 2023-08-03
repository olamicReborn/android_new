package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.TransferRewardsRequest
import com.maggnet.data.settings.model.TransferRewardsResponse
import com.maggnet.data.settings.service.SettingsApiServices
import io.reactivex.Single
import javax.inject.Inject

class TransferRewardsDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : TransferRewardsRepository {


    override fun transferRewards(transferRewardsRequest: TransferRewardsRequest): Single<TransferRewardsResponse> {
        return settingsApiServices.transferRewards(transferRewardsRequest)
    }
}