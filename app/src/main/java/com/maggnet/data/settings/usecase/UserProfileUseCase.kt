package com.maggnet.data.settings.usecase

import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.settings.model.UserProfileRequest
import com.maggnet.data.settings.remote.UserProfileSettingRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import com.maggnet.utils.PreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(
    private val userProfileSettingRepository: UserProfileSettingRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<RegisterUserResponse, UserProfileUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<RegisterUserResponse> {
        return userProfileSettingRepository.updateProfile(params!!.userId, params!!.userProfileRequest)
    }

    data class Params constructor(
        val userProfileRequest: UserProfileRequest,
        var userId: String
    ) {
        companion object {
            fun create(userid : String, userProfileRequest: UserProfileRequest) =
                Params(
                    userProfileRequest,
                    userid,
                )
        }
    }
}