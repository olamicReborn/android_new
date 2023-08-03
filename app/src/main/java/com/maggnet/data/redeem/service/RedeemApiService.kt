package com.maggnet.data.redeem.service

import com.maggnet.data.redeem.model.*
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface RedeemApiService {

    @POST("api/verify-device")
    fun verifyDevice(@Body verifyDeviceRequest: VerifyDeviceRequest): Single<VerifyDeviceResponse>

    @POST("foodics-loyalty/reward-megnatis-2")
    fun rewardCoupon(@Body rawBody: RequestBody): Single<RewardCouponResponse>

    @POST("foodics-loyalty/redeem-megnatis-2")
    fun redeemCoupon(@Body rawBody: RequestBody): Single<RedeemCouponResponse>

    @POST("validate-coupon")
    fun couponValidity(@Body rawBody: RequestBody): Single<BaseResponse>

}