package com.maggnet.data.languagechange.remote


import com.maggnet.data.languagechange.model.AppLanguageChangeRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface AppLanguageRepository {

    fun changeAppLanguage(appLanguageChangeRequest: AppLanguageChangeRequest): Single<BaseResponse>

}