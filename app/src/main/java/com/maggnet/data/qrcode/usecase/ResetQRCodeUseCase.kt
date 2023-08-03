package com.maggnet.data.qrcode.usecase

import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.remote.QRCodeRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ResetQRCodeUseCase @Inject constructor(
    private val qrCodeRepository: QRCodeRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ResetQRCodeResponse, ResetQRCodeUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<ResetQRCodeResponse> {
        return qrCodeRepository.resetQRCode(params!!.resetQRCodeRequest)
    }

    data class Params constructor(
        val resetQRCodeRequest: ResetQRCodeRequest
    ) {
        companion object {
            fun create(resetQRCodeRequest: ResetQRCodeRequest) =
                Params(
                    resetQRCodeRequest
                )
        }
    }
}