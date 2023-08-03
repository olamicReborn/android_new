package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.AddToCartRequest
import com.maggnet.data.coupons.model.AddToWishListRequest
import com.maggnet.data.coupons.remote.AddToCartRepository
import com.maggnet.data.coupons.remote.AddToWishListRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class AddToWishListUseCase @Inject constructor(
    private val addToWishListRepository: AddToWishListRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, AddToWishListUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return addToWishListRepository.addToWishList(params!!.addToWishListRequest)
    }

    data class Params constructor(
        val addToWishListRequest: AddToWishListRequest
    ) {
        companion object {
            fun create(addToWishListRequest: AddToWishListRequest) =
                Params(
                    addToWishListRequest
                )
        }
    }
}