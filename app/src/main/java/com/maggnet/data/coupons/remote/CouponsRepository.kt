package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.*
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.ResponseBody

interface CouponsRepository {
    fun getCouponsList(couponsListRequest: CouponsListRequest): Single<CouponsListResponse>

    fun getBannersList(): Single<BannerListResponse>

    fun getCartList(getCartListRequest: GetCartListRequest): Single<CouponsListResponse>

    fun getViewAllWalletCoupons(getCartListRequest: GetCartListRequest): Single<ViewAllCategoriesResponse>

    fun deleteCoupon(deleteCouponRequest: DeleteCouponRequest): Single<BaseResponse>

    fun couponlist(couponsListImeiRequest: CouponsListImeiRequest): Single<CouponsListImeiResponse>


}