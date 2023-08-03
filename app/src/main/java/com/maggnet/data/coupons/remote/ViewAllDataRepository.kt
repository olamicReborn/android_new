package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.ViewAllCategoriesRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import com.maggnet.data.coupons.services.CouponsApiServices
import io.reactivex.Single
import javax.inject.Inject

class ViewAllDataRepository @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : ViewAllRepository {

    override fun getViewAllCategories(viewAllCategoriesRequest: ViewAllCategoriesRequest): Single<ViewAllCategoriesResponse> {
        return couponsApiServices.getViewAllCategories(viewAllCategoriesRequest)
    }
}