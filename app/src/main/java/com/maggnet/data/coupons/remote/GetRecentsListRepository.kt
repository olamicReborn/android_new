package com.maggnet.data.coupons.remote

import com.maggnet.data.coupons.model.*
import io.reactivex.Single

interface GetRecentsListRepository {

    fun getRecentList(getRecentListRequest: GetRecentListRequest): Single<CouponsListResponse>

}