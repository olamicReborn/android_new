package com.maggnet.data.settings.usecase

import com.maggnet.data.settings.model.RewardsHistoryRequest
import com.maggnet.data.settings.model.RewardsHistoryResponse
import com.maggnet.data.settings.remote.RewardsHistoryRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRewardsHistoryUseCase @Inject constructor(
    private val rewardsHistoryRepository: RewardsHistoryRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RewardsHistoryResponse, GetRewardsHistoryUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<RewardsHistoryResponse> {
        return rewardsHistoryRepository.getRewardsHistory(params!!.rewardsHistoryRequest)
    }

    data class Params constructor(
        val rewardsHistoryRequest: RewardsHistoryRequest,

    ) {
        companion object {
            fun create(rewardsHistoryRequest: RewardsHistoryRequest) =
                Params(
                    rewardsHistoryRequest,
                )
        }
    }
}