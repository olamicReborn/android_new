package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.RedeemCouponResponse
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface RedeemRepository {

    fun redeemCoupon(requestBody: RequestBody): Single<RedeemCouponResponse>
}