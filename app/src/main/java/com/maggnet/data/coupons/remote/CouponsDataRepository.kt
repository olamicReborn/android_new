package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.*
import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class CouponsDataRepository @Inject constructor(
    private val couponsApiServices : CouponsApiServices
) : CouponsRepository {
    override fun getCouponsList(couponsListRequest: CouponsListRequest): Single<CouponsListResponse> {
        return couponsApiServices.getCouponsList(couponsListRequest)
    }

    override fun getBannersList(): Single<BannerListResponse> {
        return couponsApiServices.getBanner()
    }

    override fun getCartList(getCartListRequest: GetCartListRequest): Single<CouponsListResponse> {
        return couponsApiServices.getCartList(getCartListRequest)
    }

    override fun getViewAllWalletCoupons(getCartListRequest: GetCartListRequest): Single<ViewAllCategoriesResponse> {
        return couponsApiServices.getViewAllWalletCoupons(getCartListRequest)
    }

    override fun deleteCoupon(deleteCouponRequest: DeleteCouponRequest): Single<BaseResponse> {
        return couponsApiServices.deleteCoupon(deleteCouponRequest)
    }

    override fun couponlist(couponsListImeiRequest: CouponsListImeiRequest): Single<CouponsListImeiResponse> {
        return couponsApiServices.getCouponsListByImei(couponsListImeiRequest)
    }
}