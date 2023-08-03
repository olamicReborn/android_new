package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.ShopDetailRequest
import com.maggnet.data.coupons.model.ShopDetailResponse
import com.maggnet.data.coupons.remote.ShopDetailRepository
import com.maggnet.data.settings.model.UserProfileRequest
import com.maggnet.data.settings.usecase.UserProfileUseCase
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ShopDetailUseCase @Inject constructor(
    private val shopDetailRepository: ShopDetailRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ShopDetailResponse, ShopDetailUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Single<ShopDetailResponse> {
        return shopDetailRepository.getShopDetails(params!!.id, params!!.shopDetailRequest)
    }

    data class Params constructor(
        var shopDetailRequest: ShopDetailRequest,
        var id: String
    ) {
        companion object {
            fun create(id : String, shopDetailRequest: ShopDetailRequest) =
                Params(
                    shopDetailRequest,
                    id,
                )
        }
    }

}