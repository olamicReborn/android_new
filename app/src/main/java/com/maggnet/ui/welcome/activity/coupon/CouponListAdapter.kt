package com.maggnet.ui.welcome.activity.coupon

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.databinding.LayoutCouponBinding
import com.maggnet.ui.base.BaseRecyclerAdapter
import com.maggnet.ui.base.BaseViewHolder
import com.maggnet.ui.extensions.empty

class CouponListAdapter(private var couponListener: CouponListener,var context: Context) : BaseRecyclerAdapter<CouponsListImeiResponse.CouponListData>(){

    var originList = listOf<CouponsListImeiResponse.CouponListData>()
    var searchText: String = String.empty

    // start OVERRIDDEN
    override fun createBaseViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CouponsListImeiResponse.CouponListData> {
        return CouponViewHolder(
            context,
            LayoutCouponBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), couponListener
        )
    }

    interface CouponListener {
        fun onItemClick(item : CouponsListImeiResponse.CouponListData)
    }


}