package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.service.LoginApiServices
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.data.register.otp.service.RegisterApiServices
import io.reactivex.Single
import javax.inject.Inject

class LoginUserDataRepository @Inject constructor(
    private val loginApiServices : LoginApiServices
) : LoginUserRepository {
    override fun loginUser(loginUserRequest: LoginUserRequest): Single<LoginUserResponse> {
        return loginApiServices.loginUser(loginUserRequest)
    }
}