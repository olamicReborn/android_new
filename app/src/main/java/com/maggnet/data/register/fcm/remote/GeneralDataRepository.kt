package com.maggnet.data.register.fcm.remote

import com.maggnet.data.register.fcm.model.FCMTokenResponse
import com.maggnet.data.register.fcm.model.FcmTokenRequest
import com.maggnet.data.register.fcm.service.GeneralApiService
import io.reactivex.Single
import javax.inject.Inject

class GeneralDataRepository @Inject constructor(
    private val generalApiService: GeneralApiService
) : GeneralRepository {

    override fun saveFcmToken(fcmTokenRequest: FcmTokenRequest): Single<FCMTokenResponse> {
        return generalApiService.saveFcmToken(fcmTokenRequest)
    }
}