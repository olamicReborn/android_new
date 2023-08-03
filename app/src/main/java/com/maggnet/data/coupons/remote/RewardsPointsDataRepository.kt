package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.RewardsPointsRequest
import com.maggnet.data.coupons.model.RewardsPointsResponse
import com.maggnet.data.coupons.services.CouponsApiServices
import io.reactivex.Single
import javax.inject.Inject

class RewardsPointsDataRepository @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : RewardsPointsRepository {

    override fun getRewardsPoints(rewardsPointsRequest: RewardsPointsRequest): Single<RewardsPointsResponse> {
        return couponsApiServices.getRewardsPoints(rewardsPointsRequest)
    }
}