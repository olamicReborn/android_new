package com.maggnet.data.qrcode.usecase

import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.QRCodeResponseByImei
import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.remote.QRCodeByImeiRepository
import com.maggnet.data.qrcode.remote.QRCodeRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetQRCodeUseCase @Inject constructor(
    private val qrCodeByImeiRepository: QRCodeByImeiRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<QRCodeResponseByImei, GetQRCodeUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<QRCodeResponseByImei> {
        return qrCodeByImeiRepository.getQRCode(params!!.qrCodeRequestByImei)
    }

    data class Params constructor(
        val qrCodeRequestByImei: QRCodeRequestByImei
    ) {
        companion object {
            fun create(qrCodeRequestByImei: QRCodeRequestByImei) =
                Params(
                    qrCodeRequestByImei
                )
        }
    }
}