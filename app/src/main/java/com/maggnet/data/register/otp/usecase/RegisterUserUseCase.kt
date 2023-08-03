package com.maggnet.data.register.otp.usecase

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.remote.CheckRegisteredUserRepository
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registerUserRepository : RegisterUserRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RegisterUserResponse, RegisterUserUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<RegisterUserResponse> {
        return registerUserRepository.registerUser(params!!.registerUserRequest)
    }

    data class Params constructor(
        val registerUserRequest: RegisterUserRequest
    ) {
        companion object {
            fun create(registerUserRequest: RegisterUserRequest) =
                Params(
                    registerUserRequest
                )
        }
    }
}