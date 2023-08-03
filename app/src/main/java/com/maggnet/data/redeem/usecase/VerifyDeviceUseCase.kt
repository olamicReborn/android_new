package com.maggnet.data.redeem.usecase

import com.maggnet.data.redeem.model.VerifyDeviceRequest
import com.maggnet.data.redeem.model.VerifyDeviceResponse
import com.maggnet.data.redeem.remote.VerifyDeviceRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class VerifyDeviceUseCase @Inject constructor(
    private val verifyDeviceRepository: VerifyDeviceRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<VerifyDeviceResponse, VerifyDeviceUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<VerifyDeviceResponse> {
        return verifyDeviceRepository.verifyDevice(params!!.verifyDeviceRequest)
    }

    data class Params constructor(
        val verifyDeviceRequest: VerifyDeviceRequest
    ) {
        companion object {
            fun create(verifyDeviceRequest: VerifyDeviceRequest) =
                Params(
                    verifyDeviceRequest
                )
        }
    }
}