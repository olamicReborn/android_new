package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.ShopDetailResponse
import com.maggnet.data.coupons.model.UserCouponResponse
import com.maggnet.data.coupons.remote.ShopDetailRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class UserCouponUseCase @Inject constructor(
    private val shopDetailRepository: ShopDetailRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<UserCouponResponse, UserCouponUseCase.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Single<UserCouponResponse> {
        return shopDetailRepository.userCouponView(params!!.id)
    }

    data class Params constructor(
        var id: String
    ) {
        companion object {
            fun create(id: String) = Params(id)
        }
    }

}