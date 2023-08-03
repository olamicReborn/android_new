package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.ShopListRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import io.reactivex.Single

interface ShopsListRepository {

    fun getShopsList(shopsListRequest: ShopListRequest): Single<CouponsListResponse>

    fun getShopsViewAll(shopsListRequest: ShopListRequest): Single<ViewAllCategoriesResponse>

}