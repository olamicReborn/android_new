package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.AddToWishListRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface AddToWishListRepository {

    fun addToWishList(addToWishListRequest: AddToWishListRequest): Single<BaseResponse>

}