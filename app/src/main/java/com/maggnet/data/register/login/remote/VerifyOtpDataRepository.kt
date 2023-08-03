package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.model.VerifyOtpRequest
import com.maggnet.data.register.login.service.LoginApiServices
import io.reactivex.Single
import javax.inject.Inject

class VerifyOtpDataRepository @Inject constructor(
    private val loginApiServices : LoginApiServices
) : VerifyOtpRepository {
    override fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Single<ForgotPasswordResponse> {
        return loginApiServices.verifyOtp(verifyOtpRequest)
    }
}