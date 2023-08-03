package com.maggnet.data.register.fcm.service

import com.maggnet.data.register.fcm.model.FCMTokenResponse
import com.maggnet.data.register.fcm.model.FcmTokenRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface GeneralApiService {

    @POST("api/user/save/firebase-token")
    fun saveFcmToken(@Body fcmTokenRequest: FcmTokenRequest): Single<FCMTokenResponse>
}