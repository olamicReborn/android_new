package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.RewardsPointsRequest
import com.maggnet.data.coupons.model.RewardsPointsResponse
import io.reactivex.Single

interface RewardsPointsRepository {

    fun getRewardsPoints(rewardsPointsRequest: RewardsPointsRequest): Single<RewardsPointsResponse>

}