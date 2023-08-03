package com.maggnet.data.register.imageupload.remote

import com.maggnet.data.register.imageupload.model.ImageResponse
import io.reactivex.Single
import okhttp3.MultipartBody

interface ImageRepository {

    fun uploadUserImage(fileBody: MultipartBody.Part?): Single<ImageResponse>
}