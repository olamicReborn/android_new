package com.maggnet.data.register.imageupload.usecase


import com.maggnet.data.register.imageupload.model.ImageResponse
import com.maggnet.data.register.imageupload.model.UploadImageRequest
import com.maggnet.data.register.imageupload.remote.ImageRepository
import com.maggnet.domain.executor.PostExecutionThread
import io.reactivex.Single
import javax.inject.Inject

class OptimizeImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCaseWithMultipart<ImageResponse, OptimizeImageUseCase>(postExecutionThread) {

    override fun buildUseCase(imageRequest: UploadImageRequest): Single<ImageResponse> {
        return imageRepository.uploadUserImage(imageRequest.fileBody)
    }
}