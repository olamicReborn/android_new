package com.maggnet.data.register.login.model

import com.google.gson.annotations.SerializedName
import com.maggnet.data.register.otp.model.UserData
import com.maggnet.domain.remote.BaseResponse

class ForgotPasswordResponse (@SerializedName("data") val data: UserData = UserData()
) : BaseResponse() {
}