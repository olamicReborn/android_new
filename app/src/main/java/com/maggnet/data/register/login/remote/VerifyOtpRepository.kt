package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.model.VerifyOtpRequest
import io.reactivex.Single

interface VerifyOtpRepository {
    fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Single<ForgotPasswordResponse>
}