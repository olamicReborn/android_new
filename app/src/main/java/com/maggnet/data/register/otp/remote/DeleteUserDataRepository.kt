package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.model.ScanUserRequest
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.data.register.otp.service.RegisterApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class DeleteUserDataRepository @Inject constructor(
    private val registerApiServices: RegisterApiServices
) : DeleteUserRepository {
    override fun deleteUser(userRequest: UserRequest): Single<BaseResponse> {
        return registerApiServices.deleteinvalidUser(userRequest)
    }

    override fun deleteScanUser(userRequest: ScanUserRequest): Single<BaseResponse> {
        return registerApiServices.deletescanUser(userRequest)
    }

}