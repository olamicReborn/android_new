package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.BusinessNotificationRequest
import com.maggnet.data.coupons.model.ShopDetailRequest
import com.maggnet.data.coupons.model.ShopDetailResponse
import com.maggnet.data.coupons.model.UserCouponResponse
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single

interface ShopDetailRepository {

    fun getShopDetails(id: String, shopDetailRequest: ShopDetailRequest): Single<ShopDetailResponse>

    fun userCouponView(id: String) : Single<UserCouponResponse>

    fun businessNotification(businessNotificationRequest: BusinessNotificationRequest) : Single<BaseResponse>



}