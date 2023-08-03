package com.maggnet.data.coupons.usecase


import com.maggnet.data.coupons.model.GetWishListRequest
import com.maggnet.data.coupons.model.GetWishListResponse
import com.maggnet.data.coupons.remote.GetWishListItemsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetWishListDataUseCase  @Inject constructor(
    private val getWishListItemsRepository: GetWishListItemsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<GetWishListResponse, GetWishListDataUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<GetWishListResponse> {
        return getWishListItemsRepository.getWishList(params!!.getWishListRequest)
    }

    data class Params constructor(
        val getWishListRequest: GetWishListRequest
    ) {
        companion object {
            fun create(getWishListRequest: GetWishListRequest) =
                Params(
                    getWishListRequest
                )
        }
    }
}