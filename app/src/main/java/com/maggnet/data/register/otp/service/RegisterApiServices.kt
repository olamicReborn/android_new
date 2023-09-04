package com.maggnet.data.register.otp.service

import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.ScanQRCodeResponseByImei
import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.RegisterUserRequest
import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.register.otp.model.ScanUserRequest
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface RegisterApiServices {

    @POST("api/user/check")
    fun checkRegisteredUser(@Body checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>

    @POST("api/user/check-or-register")
    fun checkRegisteredUserOrRegister(@Body checkRegisteredUserRequest: CheckRegisteredUserRequest): Single<CheckRegisteredUserResponse>

    @POST("api/user/register")
    fun registerUser(@Body registeredUserRequest: RegisterUserRequest): Single<RegisterUserResponse>

    @POST("api/user/delete-user-by-user-id")
    fun deleteinvalidUser(@Body userRequest: UserRequest): Single<BaseResponse>

    @POST("api/user/delete-scanned-user")
    fun deletescanUser(@Body userRequest: ScanUserRequest): Single<BaseResponse>

}