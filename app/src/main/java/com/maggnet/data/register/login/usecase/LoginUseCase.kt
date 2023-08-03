package com.maggnet.data.register.login.usecase

import com.google.gson.annotations.SerializedName
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.data.register.otp.usecase.RegisterUserUseCase
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginUserRepository: LoginUserRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<LoginUserResponse, LoginUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<LoginUserResponse> {
        return loginUserRepository.loginUser(params!!.loginUserRequest)
    }

    data class Params constructor(
        val loginUserRequest: LoginUserRequest
    ) {
        companion object {
            fun create(loginUserRequest: LoginUserRequest) =
                Params(
                    loginUserRequest
                )
        }
    }
}