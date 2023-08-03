package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.ViewAllCategoriesRequest
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import io.reactivex.Single

interface ViewAllRepository {

    fun getViewAllCategories(viewAllCategoriesRequest: ViewAllCategoriesRequest): Single<ViewAllCategoriesResponse>
}