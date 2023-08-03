package com.maggnet.data.register.login.service

import com.maggnet.data.register.login.model.ForgotPasswordRequest
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.model.VerifyOtpRequest
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiServices {

    @POST("api/user/login-with-email")
    fun loginUser(@Body loginUserRequest: LoginUserRequest): Single<LoginUserResponse>

    @POST("api/user/forgot-password")
    fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Single<ForgotPasswordResponse>


    @POST("api/user/generate-otp")
    fun sendOtp(@Body sendOtpRequest: SendOtpRequest): Single<ForgotPasswordResponse>

    @POST("api/user/verify-otp")
    fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Single<ForgotPasswordResponse>
}