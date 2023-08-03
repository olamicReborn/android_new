package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import io.reactivex.Single

interface CheckRegisteredUserRepository {
    fun checkRegisteredUser(checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>
    fun checkRegisteredUserOrRegister(checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>
}