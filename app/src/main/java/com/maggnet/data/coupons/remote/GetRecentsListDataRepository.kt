package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.GetRecentListRequest
import com.maggnet.data.coupons.services.CouponsApiServices
import io.reactivex.Single
import javax.inject.Inject

class GetRecentsListDataRepository  @Inject constructor(
    private val couponsApiServices: CouponsApiServices
) : GetRecentsListRepository {


    override fun getRecentList(getRecentListRequest: GetRecentListRequest): Single<CouponsListResponse> {
        return couponsApiServices.getRecentList(getRecentListRequest)
    }
}