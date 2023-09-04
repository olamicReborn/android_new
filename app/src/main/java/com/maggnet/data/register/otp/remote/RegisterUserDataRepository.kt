package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.service.RegisterApiServices
import io.reactivex.Single
import javax.inject.Inject

class RegisterUserDataRepository @Inject constructor(
    private val registerApiServices: RegisterApiServices
) : RegisterUserRepository {
    override fun registerUser(registerUserRequest: RegisterUserRequest): Single<RegisterUserResponse> {
        return registerApiServices.registerUser(registerUserRequest)
    }

}