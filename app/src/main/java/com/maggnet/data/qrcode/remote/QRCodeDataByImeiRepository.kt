package com.maggnet.data.qrcode.remote

import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.QRCodeResponseByImei
import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.model.ScanQRCodeResponseByImei
import com.maggnet.data.qrcode.service.QRCodeApiServices
import io.reactivex.Single
import javax.inject.Inject

class QRCodeDataByImeiRepository @Inject constructor(
    private val qrCodeApiServices : QRCodeApiServices
) : QRCodeByImeiRepository {
    override fun getQRCode(qrCodeRequestByImei: QRCodeRequestByImei): Single<QRCodeResponseByImei> {
        return qrCodeApiServices.qrCodeByImei(qrCodeRequestByImei)
    }

    override fun getStatusQRCode(qrCodeRequestByImei: QRCodeRequestByImei): Single<ScanQRCodeResponseByImei> {
        return qrCodeApiServices.qrStatusCodeByImei(qrCodeRequestByImei)
    }
}