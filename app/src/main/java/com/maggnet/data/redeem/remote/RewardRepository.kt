package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.RewardCouponResponse
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface RewardRepository {

    fun rewardCoupon(requestBody: RequestBody): Single<RewardCouponResponse>

}

