package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.*
import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class AddToCartDataRepository @Inject constructor(
    private val couponsApiServices : CouponsApiServices
) : AddToCartRepository {
    override fun addToCart(addToCartRequest: AddToCartRequest): Single<BaseResponse> {
        return couponsApiServices.addToCart(addToCartRequest)
    }

}