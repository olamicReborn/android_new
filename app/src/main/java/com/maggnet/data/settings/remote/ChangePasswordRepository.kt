package com.maggnet.data.settings.remote

import com.maggnet.data.settings.model.ChangePasswordRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface ChangePasswordRepository {
    fun changePassword(changePasswordRequest: ChangePasswordRequest): Single<BaseResponse>
}