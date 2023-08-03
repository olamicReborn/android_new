package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.AddToCartRequest
import com.maggnet.data.coupons.remote.AddToCartRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val addToCartRepository: AddToCartRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, AddToCartUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return addToCartRepository.addToCart(params!!.addToCartRequest)
    }

    data class Params constructor(
        val addToCartRequest: AddToCartRequest
    ) {
        companion object {
            fun create(addToCartRequest: AddToCartRequest) =
                Params(
                    addToCartRequest
                )
        }
    }
}