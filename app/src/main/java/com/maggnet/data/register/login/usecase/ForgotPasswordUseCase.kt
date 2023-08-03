package com.maggnet.data.register.login.usecase

import com.google.gson.annotations.SerializedName
import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.remote.ForgotPasswordRepository
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ForgotPasswordResponse, ForgotPasswordUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<ForgotPasswordResponse> {
        return forgotPasswordRepository.forgotPassword(params!!.forgotPasswordRequest)
    }

    data class Params constructor(
        val forgotPasswordRequest: ForgotPasswordRequest
    ) {
        companion object {
            fun create(forgotPasswordRequest: ForgotPasswordRequest) =
                Params(
                    forgotPasswordRequest
                )
        }
    }
}
