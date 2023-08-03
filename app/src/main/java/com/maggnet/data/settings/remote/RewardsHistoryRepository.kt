package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.RewardsHistoryRequest
import com.maggnet.data.settings.model.RewardsHistoryResponse
import io.reactivex.Single

interface RewardsHistoryRepository {
    fun getRewardsHistory(rewardsHistoryRequest: RewardsHistoryRequest): Single<RewardsHistoryResponse>
}