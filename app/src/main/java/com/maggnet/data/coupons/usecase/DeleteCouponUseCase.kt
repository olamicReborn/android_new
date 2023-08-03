package com.maggnet.data.coupons.usecase


import com.maggnet.data.coupons.model.DeleteCouponRequest
import com.maggnet.data.coupons.remote.CouponsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class DeleteCouponUseCase @Inject constructor(
    private val couponsRepository: CouponsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, DeleteCouponUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return couponsRepository.deleteCoupon(params!!.deleteCouponRequest)
    }

    data class Params constructor(
        val deleteCouponRequest: DeleteCouponRequest
    ) {
        companion object {
            fun create(deleteCouponRequest: DeleteCouponRequest) =
                Params(
                    deleteCouponRequest
                )
        }
    }
}