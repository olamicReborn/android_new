package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.CouponsListRequest
import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.GetCartListRequest
import com.maggnet.data.coupons.remote.CouponsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetCartListUseCase @Inject constructor(
    private val couponsRepository: CouponsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CouponsListResponse, GetCartListUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<CouponsListResponse> {
        return couponsRepository.getCartList(params!!.getCartListRequest)
    }


    data class Params constructor(
        val getCartListRequest: GetCartListRequest
    ) {
        companion object {
            fun create(getCartListRequest: GetCartListRequest) =
                Params(
                    getCartListRequest
                )
        }
    }
}