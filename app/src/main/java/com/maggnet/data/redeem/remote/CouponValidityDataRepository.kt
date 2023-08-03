package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.service.RedeemApiService
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.RequestBody
import javax.inject.Inject

class CouponValidityDataRepository @Inject constructor(
    private val redeemApiService: RedeemApiService
) : CouponValidityRepository {

    override fun couponValidity(requestBody: RequestBody): Single<BaseResponse> {
        return redeemApiService.couponValidity(requestBody)
    }
}