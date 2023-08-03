package com.maggnet.data.redeem.usecase

import com.maggnet.data.redeem.remote.CouponValidityRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import okhttp3.RequestBody
import javax.inject.Inject

class CouponValidityUseCase @Inject constructor(
    private val couponValidityRepository: CouponValidityRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, CouponValidityUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return couponValidityRepository.couponValidity(params?.requestBody!!)
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
