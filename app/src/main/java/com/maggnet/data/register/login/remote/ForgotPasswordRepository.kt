package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import io.reactivex.Single

interface ForgotPasswordRepository {
    fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Single<ForgotPasswordResponse>
}