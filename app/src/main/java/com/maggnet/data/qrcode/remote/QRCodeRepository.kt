package com.maggnet.data.qrcode.remote

import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse

import io.reactivex.Single

interface QRCodeRepository {
    fun resetQRCode(resetQRCodeRequest: ResetQRCodeRequest): Single<ResetQRCodeResponse>
}