package com.maggnet.data.languagechange.usecase


import com.maggnet.data.languagechange.model.AppLanguageChangeRequest
import com.maggnet.data.languagechange.remote.AppLanguageRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class AppLanguageChangeUseCase @Inject constructor(
    private val appLanguageRepository: AppLanguageRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, AppLanguageChangeUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return appLanguageRepository.changeAppLanguage(params!!.appLanguageChangeRequest)
    }

    data class Params constructor(
        val appLanguageChangeRequest: AppLanguageChangeRequest
    ) {
        companion object {
            fun create(appLanguageChangeRequest: AppLanguageChangeRequest) =
                Params(
                    appLanguageChangeRequest
                )
        }
    }
}