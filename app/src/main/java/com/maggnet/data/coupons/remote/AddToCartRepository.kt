package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.AddToCartRequest
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface AddToCartRepository {

    fun addToCart(addToCartRequest: AddToCartRequest): Single<BaseResponse>

}