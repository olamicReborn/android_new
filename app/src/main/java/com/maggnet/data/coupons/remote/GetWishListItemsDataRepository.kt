package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.DeleteWishListRequest
import com.maggnet.data.coupons.model.GetWishListRequest
import com.maggnet.data.coupons.model.GetWishListResponse
import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class GetWishListItemsDataRepository @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : GetWishListItemsRepository {
    override fun getWishList(wishListRequest: GetWishListRequest): Single<GetWishListResponse> {
        return couponsApiServices.getWishListItems(wishListRequest)
    }

    override fun deleteWishList(deleteWishListRequest: DeleteWishListRequest): Single<BaseResponse> {
        return couponsApiServices.deleteWishList(deleteWishListRequest)
    }
}