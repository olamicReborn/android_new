package com.maggnet.ui.welcome.activity.coupon

import com.maggnet.data.coupons.model.CouponsListImeiRequest
import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.data.coupons.model.ViewAllCategoriesRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import com.maggnet.data.coupons.remote.CouponsRepository
import com.maggnet.data.coupons.remote.ViewAllRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CouponListUseCase @Inject constructor(
    private val couponsRepository: CouponsRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CouponsListImeiResponse, CouponListUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<CouponsListImeiResponse> {
        return couponsRepository.couponlist(params!!.couponsListImeiRequest)
    }

    data class Params constructor(
        val couponsListImeiRequest: CouponsListImeiRequest
    ) {
        companion object {
            fun create(couponsListImeiRequest: CouponsListImeiRequest) =
                Params(
                    couponsListImeiRequest
                )
        }
    }


}