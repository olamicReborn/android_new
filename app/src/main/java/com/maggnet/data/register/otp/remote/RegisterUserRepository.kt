package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import io.reactivex.Single

interface RegisterUserRepository {
    fun registerUser(registerUserRequest: RegisterUserRequest): Single<RegisterUserResponse>
}