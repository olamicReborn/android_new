package com.maggnet.data.redeem.remote

import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.RequestBody

interface CouponValidityRepository {

    fun couponValidity(requestBody: RequestBody): Single<BaseResponse>
}