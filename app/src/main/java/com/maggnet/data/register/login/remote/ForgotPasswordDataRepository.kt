package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.service.LoginApiServices
import io.reactivex.Single
import javax.inject.Inject

class ForgotPasswordDataRepository @Inject constructor(
    private val loginApiServices : LoginApiServices
) : ForgotPasswordRepository {
    override fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Single<ForgotPasswordResponse> {
        return loginApiServices.forgotPassword(forgotPasswordRequest)
    }

}