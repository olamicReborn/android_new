package com.maggnet.data.qrcode.remote

import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.QRCodeResponseByImei
import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.model.ScanQRCodeResponseByImei

import io.reactivex.Single

interface QRCodeByImeiRepository {
    fun getQRCode(qrCodeRequestByImei: QRCodeRequestByImei): Single<QRCodeResponseByImei>
    fun getStatusQRCode(qrCodeRequestByImei: QRCodeRequestByImei): Single<ScanQRCodeResponseByImei>
}