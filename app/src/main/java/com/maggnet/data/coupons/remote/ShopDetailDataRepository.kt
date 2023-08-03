package com.maggnet.data.coupons.remote


import com.maggnet.data.coupons.model.BusinessNotificationRequest
import com.maggnet.data.coupons.model.ShopDetailRequest
import com.maggnet.data.coupons.model.ShopDetailResponse
import com.maggnet.data.coupons.model.UserCouponResponse
import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class ShopDetailDataRepository @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : ShopDetailRepository {
    override fun getShopDetails(
        id: String,
        shopDetailRequest: ShopDetailRequest
    ): Single<ShopDetailResponse> {
        return couponsApiServices.getShopDetails(id, shopDetailRequest)
    }

    override fun userCouponView(id: String): Single<UserCouponResponse> {
        return couponsApiServices.userCouponView(id)
    }

    override fun businessNotification(businessNotificationRequest: BusinessNotificationRequest): Single<BaseResponse> {
        return couponsApiServices.businessNotification(businessNotificationRequest)
    }
}