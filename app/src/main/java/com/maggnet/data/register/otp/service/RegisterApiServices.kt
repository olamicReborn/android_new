package com.maggnet.data.register.otp.service

import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiServices {

    @POST("api/user/check")
    fun checkRegisteredUser(@Body checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>

    @POST("api/user/check-or-register")
    fun checkRegisteredUserOrRegister(@Body checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>

    @POST("api/user/register")
    fun registerUser(@Body registeredUserRequest: RegisterUserRequest): Single<RegisterUserResponse>
}