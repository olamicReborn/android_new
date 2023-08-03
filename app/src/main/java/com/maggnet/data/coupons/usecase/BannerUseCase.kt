package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.BannerListResponse
import com.maggnet.data.coupons.remote.CouponsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class BannerUseCase @Inject constructor(
    private val couponsRepository: CouponsRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BannerListResponse, Void>(postExecutionThread) {

    override fun buildUseCase(params: Void?): Single<BannerListResponse> {
        return couponsRepository.getBannersList()
    }
}