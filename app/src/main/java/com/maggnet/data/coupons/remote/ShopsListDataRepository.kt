package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.ShopListRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import com.maggnet.data.coupons.services.CouponsApiServices
import io.reactivex.Single
import javax.inject.Inject

class ShopsListDataRepository @Inject constructor(
    private val couponsApiServices : CouponsApiServices
) : ShopsListRepository {

    override fun getShopsList(shopsListRequest: ShopListRequest): Single<CouponsListResponse> {
        return couponsApiServices.getShopsList(shopsListRequest)
    }

    override fun getShopsViewAll(shopsListRequest: ShopListRequest): Single<ViewAllCategoriesResponse> {
        return couponsApiServices.getShopsViewAll(shopsListRequest)
    }
}