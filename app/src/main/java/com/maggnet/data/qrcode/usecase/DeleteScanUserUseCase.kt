package com.maggnet.data.qrcode.usecase

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.model.ScanUserRequest
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.data.register.otp.remote.CheckRegisteredUserRepository
import com.maggnet.data.register.otp.remote.DeleteUserRepository
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class DeleteScanUserUseCase @Inject constructor(
    private val deleteUserRepository: DeleteUserRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, DeleteScanUserUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return deleteUserRepository.deleteScanUser(params!!.userRequest)
    }

    data class Params constructor(
        val userRequest: ScanUserRequest
    ) {
        companion object {
            fun create(userRequest: ScanUserRequest) =
                Params(
                    userRequest
                )
        }
    }
}