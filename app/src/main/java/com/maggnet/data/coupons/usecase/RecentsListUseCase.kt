package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.GetRecentListRequest
import com.maggnet.data.coupons.remote.GetRecentsListRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class RecentsListUseCase @Inject constructor(
    private val getRecentsListRepository: GetRecentsListRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CouponsListResponse, RecentsListUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<CouponsListResponse> {
        return getRecentsListRepository.getRecentList(params!!.getRecentListRequest)
    }


    data class Params constructor(
        val getRecentListRequest: GetRecentListRequest
    ) {
        companion object {
            fun create(getRecentListRequest: GetRecentListRequest) =
                Params(
                    getRecentListRequest
                )
        }
    }
}