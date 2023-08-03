package com.maggnet.data.settings.usecase


import com.maggnet.data.settings.model.TransferRewardsRequest
import com.maggnet.data.settings.model.TransferRewardsResponse
import com.maggnet.data.settings.remote.TransferRewardsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class TransferRewardsUseCase @Inject constructor(
    private val transferRewardsRepository: TransferRewardsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<TransferRewardsResponse, TransferRewardsUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<TransferRewardsResponse> {
        return transferRewardsRepository.transferRewards(params!!.transferRewardsRequest)
    }

    data class Params constructor(
        val transferRewardsRequest: TransferRewardsRequest,

        ) {
        companion object {
            fun create(transferRewardsRequest: TransferRewardsRequest) =
                Params(
                    transferRewardsRequest,
                )
        }
    }


}