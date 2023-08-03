package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.ShopListRequest
import com.maggnet.data.coupons.remote.ShopsListRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ShopsListUseCase @Inject constructor(
    private val shopsListRepository: ShopsListRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CouponsListResponse, ShopsListUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<CouponsListResponse> {
        return shopsListRepository.getShopsList(params!!.shopListRequest)
    }

    data class Params constructor(
        val shopListRequest: ShopListRequest
    ) {
        companion object {
            fun create(shopListRequest: ShopListRequest) =
                Params(
                    shopListRequest
                )
        }
    }
}