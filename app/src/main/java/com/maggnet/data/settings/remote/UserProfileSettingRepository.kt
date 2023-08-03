package com.maggnet.data.settings.remote

import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.settings.model.UserProfileRequest
import io.reactivex.Single

interface UserProfileSettingRepository {
    fun updateProfile(userId: String, userProfileRequest: UserProfileRequest): Single<RegisterUserResponse>
}