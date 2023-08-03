package com.maggnet.data.settings.remote


import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.settings.model.UserProfileRequest
import com.maggnet.data.settings.service.SettingsApiServices
import io.reactivex.Single
import javax.inject.Inject

class UserProfileSettingDataRepository @Inject constructor(
    private val settingsApiServices: SettingsApiServices
) : UserProfileSettingRepository {

    override fun updateProfile(userId : String, userProfileRequest: UserProfileRequest): Single<RegisterUserResponse> {
        return settingsApiServices.updateProfile(userId, userProfileRequest)

    }
}