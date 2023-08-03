package com.maggnet.data.register.login.usecase

import com.google.gson.annotations.SerializedName
import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.remote.ForgotPasswordRepository
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.login.remote.SendOtpRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val sendOtpRepository: SendOtpRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ForgotPasswordResponse, SendOtpUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<ForgotPasswordResponse> {
        return sendOtpRepository.sendOtp(params!!.sendOtpRequest)
    }

    data class Params constructor(
        val sendOtpRequest: SendOtpRequest
    ) {
        companion object {
            fun create(sendOtpRequest: SendOtpRequest) =
                Params(
                    sendOtpRequest
                )
        }
    }
}
