package com.maggnet.data.register.login.remote

import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import io.reactivex.Single

interface LoginUserRepository {

    fun loginUser(loginUserRequest: LoginUserRequest): Single<LoginUserResponse>

}