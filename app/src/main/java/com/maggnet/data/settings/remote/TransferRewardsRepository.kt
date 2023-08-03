package com.maggnet.data.settings.remote


import com.maggnet.data.settings.model.TransferRewardsRequest
import com.maggnet.data.settings.model.TransferRewardsResponse
import io.reactivex.Single

interface TransferRewardsRepository {
    fun transferRewards(transferRewardsRequest: TransferRewardsRequest): Single<TransferRewardsResponse>
}