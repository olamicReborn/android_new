package com.maggnet.data.redeem.usecase


import com.maggnet.data.redeem.model.RewardCouponResponse
import com.maggnet.data.redeem.remote.RewardRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import okhttp3.RequestBody
import javax.inject.Inject

class RewardCouponUseCase @Inject constructor(
    private val redeemRepository: RewardRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RewardCouponResponse, RewardCouponUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<RewardCouponResponse> {
        return redeemRepository.rewardCoupon(params?.requestBody!!)
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
