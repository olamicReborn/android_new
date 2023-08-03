package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.RewardsPointsRequest
import com.maggnet.data.coupons.model.RewardsPointsResponse
import com.maggnet.data.coupons.remote.RewardsPointsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class RewardsPointsUseCase @Inject constructor(
    private val rewardsPointsRepository: RewardsPointsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RewardsPointsResponse, RewardsPointsUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<RewardsPointsResponse> {
        return rewardsPointsRepository.getRewardsPoints(params!!.rewardsPointsRequest)
    }


    data class Params constructor(
        val rewardsPointsRequest: RewardsPointsRequest
    ) {
        companion object {
            fun create(rewardsPointsRequest: RewardsPointsRequest) =
                Params(
                    rewardsPointsRequest
                )
        }
    }
}