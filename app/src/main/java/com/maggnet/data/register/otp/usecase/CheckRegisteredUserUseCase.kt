package com.maggnet.data.register.otp.usecase

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.remote.CheckRegisteredUserRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CheckRegisteredUserUseCase @Inject constructor(
    private val checkRegisteredUserRepository: CheckRegisteredUserRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CheckRegisteredUserResponse, CheckRegisteredUserUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<CheckRegisteredUserResponse> {
        return checkRegisteredUserRepository.checkRegisteredUserOrRegister(params!!.checkRegisteredUserRequest)
    }

    data class Params constructor(
        val checkRegisteredUserRequest: CheckRegisteredUserRequest
    ) {
        companion object {
            fun create(checkRegisteredUserRequest: CheckRegisteredUserRequest) =
                Params(
                    checkRegisteredUserRequest
                )
        }
    }
}