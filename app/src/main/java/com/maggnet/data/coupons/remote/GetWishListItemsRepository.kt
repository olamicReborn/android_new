package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.DeleteWishListRequest
import com.maggnet.data.coupons.model.GetWishListRequest
import com.maggnet.data.coupons.model.GetWishListResponse
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface GetWishListItemsRepository {

    fun getWishList(wishListRequest: GetWishListRequest): Single<GetWishListResponse>

    fun deleteWishList(deleteWishListRequest: DeleteWishListRequest): Single<BaseResponse>

}