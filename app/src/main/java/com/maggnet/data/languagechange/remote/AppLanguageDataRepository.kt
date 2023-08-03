package com.maggnet.data.languagechange.remote

import com.maggnet.data.languagechange.model.AppLanguageChangeRequest
import com.maggnet.data.languagechange.services.AppLanguageChangeServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class AppLanguageDataRepository @Inject constructor(
    private val appLanguageChangeServices : AppLanguageChangeServices
) : AppLanguageRepository {


    override fun changeAppLanguage(appLanguageChangeRequest: AppLanguageChangeRequest): Single<BaseResponse> {
        return appLanguageChangeServices.changeAppLanguage(appLanguageChangeRequest)
    }

}
