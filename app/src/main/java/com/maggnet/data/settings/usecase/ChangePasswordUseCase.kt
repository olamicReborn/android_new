package com.maggnet.data.settings.usecase

import com.maggnet.data.settings.model.ChangePasswordRequest
import com.maggnet.data.settings.remote.ChangePasswordRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val changePasswordRepository: ChangePasswordRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, ChangePasswordUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return changePasswordRepository.changePassword(params!!.changePasswordRequest)
    }

    data class Params constructor(
        val changePasswordRequest: ChangePasswordRequest
    ) {
        companion object {
            fun create(changePasswordRequest: ChangePasswordRequest) =
                Params(
                    changePasswordRequest
                )
        }
    }
}