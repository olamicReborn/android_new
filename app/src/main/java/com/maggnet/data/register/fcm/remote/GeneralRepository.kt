package com.maggnet.data.register.fcm.remote

import com.maggnet.data.register.fcm.model.FCMTokenResponse
import com.maggnet.data.register.fcm.model.FcmTokenRequest
import io.reactivex.Single

interface GeneralRepository {
    fun saveFcmToken(fcmTokenRequest: FcmTokenRequest): Single<FCMTokenResponse>
}