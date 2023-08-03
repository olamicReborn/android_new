package com.maggnet.data.qrcode.service

import com.maggnet.data.qrcode.model.ResetQRCodeRequest
import com.maggnet.data.qrcode.model.ResetQRCodeResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface QRCodeApiServices {
    @POST("api/user/reset-qr-code")
    fun resetQRCode(@Body qrCodeRequest: ResetQRCodeRequest): Single<ResetQRCodeResponse>

}