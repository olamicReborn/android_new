package com.maggnet.ui.welcome.activity.coupon

import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.model.ViewAllCategoriesResponse
import com.maggnet.ui.base.BaseNavigator

interface CouponListNavigator : BaseNavigator {

    fun setAdapterValues(data : List<CouponsListImeiResponse.CouponListData>)

    fun displayMessage(message: String)
}