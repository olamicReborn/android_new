package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.BusinessNotificationRequest
import com.maggnet.data.coupons.remote.ShopDetailRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class BusinessNotificationUseCase @Inject constructor(
    private val shopDetailRepository: ShopDetailRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, BusinessNotificationUseCase.Params>(
    postExecutionThread
) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return shopDetailRepository.businessNotification(params!!.businessNotificationRequest)
    }

    data class Params constructor(
        val businessNotificationRequest: BusinessNotificationRequest
    ) {
        companion object {
            fun create(businessNotificationRequest: BusinessNotificationRequest) =
                Params(
                    businessNotificationRequest
                )
        }
    }
}