package com.maggnet.data.redeem.usecase

import com.maggnet.data.redeem.model.RedeemCouponResponse
import com.maggnet.data.redeem.remote.RedeemRepository
import com.maggnet.data.redeem.remote.RewardRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedRequestBodyUseCase
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject

class RedeemCouponUseCase @Inject constructor(
    private val redeemRepository: RedeemRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RedeemCouponResponse, RedeemCouponUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<RedeemCouponResponse> {
        return redeemRepository.redeemCoupon(params?.requestBody!!)
    }

    data class Params constructor(
        val requestBody: RequestBody?,
    ) {
        companion object {
            fun create(requestBody: RequestBody?) =
                Params(requestBody)
        }
    }
}
