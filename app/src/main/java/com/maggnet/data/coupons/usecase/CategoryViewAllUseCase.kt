package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.ViewAllCategoriesRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import com.maggnet.data.coupons.remote.ViewAllRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CategoryViewAllUseCase @Inject constructor(
    private val viewAllRepository: ViewAllRepository,
    postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<ViewAllCategoriesResponse, CategoryViewAllUseCase.Params>(
    postExecutionThread
) {

    override fun buildUseCase(params: Params?): Single<ViewAllCategoriesResponse> {
        return viewAllRepository.getViewAllCategories(params!!.viewAllRequest)
    }

    data class Params constructor(
        val viewAllRequest: ViewAllCategoriesRequest
    ) {
        companion object {
            fun create(viewAllRequest: ViewAllCategoriesRequest) =
                Params(
                    viewAllRequest
                )
        }
    }


}