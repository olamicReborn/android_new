package com.maggnet.data.register.fcm.usecase

import com.maggnet.data.register.fcm.model.FCMTokenResponse
import com.maggnet.data.register.fcm.model.FcmTokenRequest
import com.maggnet.data.register.fcm.remote.GeneralRepository
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.data.register.otp.usecase.RegisterUserUseCase
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class FCMTokenUseCase @Inject constructor(
    private val generalRepository  : GeneralRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<FCMTokenResponse, FCMTokenUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<FCMTokenResponse> {
        return generalRepository.saveFcmToken(params!!.fcmTokenRequest)
    }

    data class Params constructor(
        val fcmTokenRequest: FcmTokenRequest
    ) {
        companion object {
            fun create(fcmTokenRequest: FcmTokenRequest) =
                Params(
                    fcmTokenRequest
                )
        }
    }
}