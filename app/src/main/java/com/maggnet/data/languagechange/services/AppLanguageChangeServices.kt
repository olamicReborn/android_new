package com.maggnet.data.languagechange.services

import com.maggnet.data.languagechange.model.AppLanguageChangeRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AppLanguageChangeServices {

    @POST("api/user/update-app-language")
    fun changeAppLanguage(@Body appLanguageChangeRequest: AppLanguageChangeRequest): Single<BaseResponse>

}