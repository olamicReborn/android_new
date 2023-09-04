package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.model.ScanUserRequest
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface DeleteUserRepository {
    fun deleteUser(userRequest: UserRequest): Single<BaseResponse>
    fun deleteScanUser(userRequest: ScanUserRequest): Single<BaseResponse>
}