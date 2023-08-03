package com.maggnet.data.register.imageupload.remote


import com.maggnet.data.register.imageupload.model.ImageResponse
import com.maggnet.data.register.imageupload.service.ImageApiServices
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataRepository @Inject constructor(
    private val imageApiServices: ImageApiServices
) : ImageRepository {

    override fun uploadUserImage(fileBody: MultipartBody.Part?): Single<ImageResponse> {
        return imageApiServices.uploadUserImage(fileBody)
    }
}