package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.service.LoginApiServices
import io.reactivex.Single
import javax.inject.Inject

class SendOtpDataRepository @Inject constructor(
    private val loginApiServices : LoginApiServices
) : SendOtpRepository {
    override fun sendOtp(sendOtpRequest: SendOtpRequest): Single<ForgotPasswordResponse> {
        return loginApiServices.sendOtp(sendOtpRequest)
    }

}