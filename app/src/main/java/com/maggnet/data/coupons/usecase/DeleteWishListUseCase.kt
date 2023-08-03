package com.maggnet.data.coupons.usecase


import com.maggnet.data.coupons.model.DeleteWishListRequest
import com.maggnet.data.coupons.model.GetWishListRequest
import com.maggnet.data.coupons.model.GetWishListResponse
import com.maggnet.data.coupons.remote.GetWishListItemsRepository
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class DeleteWishListUseCase  @Inject constructor(
    private val getWishListItemsRepository: GetWishListItemsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<BaseResponse, DeleteWishListUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<BaseResponse> {
        return getWishListItemsRepository.deleteWishList(params!!.deleteWishListRequest)
    }

    data class Params constructor(
        val deleteWishListRequest: DeleteWishListRequest
    ) {
        companion object {
            fun create(deleteWishListRequest: DeleteWishListRequest) =
                Params(
                    deleteWishListRequest
                )
        }
    }
}