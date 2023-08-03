package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.AddToWishListRequest
import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class AddToWishListDataRepository @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : AddToWishListRepository {
    override fun addToWishList(addToWishListRequest: AddToWishListRequest): Single<BaseResponse> {
        return couponsApiServices.addToWishList(addToWishListRequest)
    }

}