package com.maggnet.data.qrcode.remote

import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.service.QRCodeApiServices
import io.reactivex.Single
import javax.inject.Inject

class QRCodeDataRepository @Inject constructor(
    private val qrCodeApiServices : QRCodeApiServices
) : QRCodeRepository {
    override fun resetQRCode(resetQRCodeRequest: ResetQRCodeRequest): Single<ResetQRCodeResponse> {
        return qrCodeApiServices.resetQRCode(resetQRCodeRequest)
    }
}