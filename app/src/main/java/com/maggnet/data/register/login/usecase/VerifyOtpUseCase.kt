package com.maggnet.data.register.login.usecase

import com.google.gson.annotations.SerializedName
import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.model.VerifyOtpRequest
import com.maggnet.data.register.login.remote.ForgotPasswordRepository
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.login.remote.SendOtpRepository
import com.maggnet.data.register.login.remote.VerifyOtpRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val verifyOtpRepository: VerifyOtpRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ForgotPasswordResponse, VerifyOtpUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<ForgotPasswordResponse> {
        return verifyOtpRepository.verifyOtp(params!!.verifyOtpRequest)
    }

    data class Params constructor(
        val verifyOtpRequest: VerifyOtpRequest
    ) {
        companion object {
            fun create(verifyOtpRequest: VerifyOtpRequest) =
                Params(
                    verifyOtpRequest
                )
        }
    }
}
