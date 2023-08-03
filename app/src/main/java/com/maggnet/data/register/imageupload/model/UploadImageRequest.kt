package com.maggnet.data.register.imageupload.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class UploadImageRequest(@SerializedName("image_file") val fileBody: MultipartBody.Part?)