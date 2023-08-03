package com.maggnet.data.register.otp.remote

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.service.RegisterApiServices
import io.reactivex.Single
import javax.inject.Inject

class CheckRegisteredUserDataRepository @Inject constructor(
    private val registerApiServices: RegisterApiServices
) : CheckRegisteredUserRepository {
    override fun checkRegisteredUser(checkRegisteredUserRequest : CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse> {
        return registerApiServices.checkRegisteredUser(checkRegisteredUserRequest)
    }

    override fun checkRegisteredUserOrRegister(checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse> {
        return registerApiServices.checkRegisteredUserOrRegister(checkRegisteredUserRequest)
    }
}