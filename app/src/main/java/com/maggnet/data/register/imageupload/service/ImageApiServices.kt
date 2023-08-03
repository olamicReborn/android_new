package com.maggnet.data.register.imageupload.service

import com.maggnet.data.register.imageupload.model.ImageResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApiServices {

    /**
     * To update user image
     */
    @POST("api/user/image-upload")
    @Multipart
    fun uploadUserImage(
        @Part file: MultipartBody.Part?
    ): Single<ImageResponse>
}