package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.RewardCouponResponse
import com.maggnet.data.redeem.service.RedeemApiService
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class RewardDataRepository @Inject constructor(
    private val redeemApiService: RedeemApiService
) : RewardRepository {

    override fun rewardCoupon(requestBody: RequestBody): Single<RewardCouponResponse> {
        return redeemApiService.rewardCoupon(
            requestBody
        )
    }
}