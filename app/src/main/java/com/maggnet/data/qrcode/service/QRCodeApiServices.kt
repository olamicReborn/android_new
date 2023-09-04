package com.maggnet.data.qrcode.service

import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.QRCodeResponseByImei
import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import com.maggnet.data.qrcode.model.ScanQRCodeResponseByImei
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface QRCodeApiServices {
    @POST("api/user/reset-qr-code")
    fun resetQRCode(@Body qrCodeRequest: ResetQRCodeRequest): Single<ResetQRCodeResponse>

    @POST("api/user/get-qr-code-by-imei")
    fun qrCodeByImei(@Body qrCodeRequestByImei: QRCodeRequestByImei): Single<QRCodeResponseByImei>

    @POST("api/user/get-scanned-user")
    fun qrStatusCodeByImei(@Body qrCodeRequestByImei: QRCodeRequestByImei): Single<ScanQRCodeResponseByImei>


}