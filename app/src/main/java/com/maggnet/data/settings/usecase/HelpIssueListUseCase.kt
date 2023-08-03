package com.maggnet.data.settings.usecase


import com.maggnet.data.settings.model.HelpIssuesListResponse
import com.maggnet.data.settings.remote.HelpSettingRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class HelpIssueListUseCase @Inject constructor(
    private val helpSettingRepository : HelpSettingRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<HelpIssuesListResponse, Void>(postExecutionThread) {

    override fun buildUseCase(params: Void?): Single<HelpIssuesListResponse> {
        return helpSettingRepository.getIssuesList()
    }
}