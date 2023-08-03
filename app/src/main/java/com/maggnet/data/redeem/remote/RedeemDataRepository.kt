package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.RedeemCouponResponse
import com.maggnet.data.redeem.service.RedeemApiService
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class RedeemDataRepository @Inject constructor(
    private val redeemApiService: RedeemApiService
) : RedeemRepository {

    override fun redeemCoupon(requestBody: RequestBody): Single<RedeemCouponResponse> {
        return redeemApiService.redeemCoupon(requestBody)
    }
}