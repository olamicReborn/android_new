package com.maggnet.ui.welcome.activity.coupon

import android.content.Context
import com.bumptech.glide.Glide
import com.maggnet.R
import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.databinding.LayoutCouponBinding
import com.maggnet.ui.base.BaseViewHolder

class CouponViewHolder(
    val context: Context, private var layoutCouponBinding: LayoutCouponBinding,
    private var couponListner: CouponListAdapter.CouponListener
) : BaseViewHolder<CouponsListImeiResponse.CouponListData>(layoutCouponBinding.root) {

    var pos =-1

    override fun bindItem(
        item: CouponsListImeiResponse.CouponListData,
        position: Int
    ) {
        layoutCouponBinding.tvPayNow.text = item.name
        layoutCouponBinding.tvTotalFees.text = "Will expire "+item.end_date


        itemView.setOnClickListener {
            pos = position
            couponListner.onItemClick(item)
            bindingAdapter?.notifyDataSetChanged()

        }

        if(position==pos){
            Glide.with(context).load(R.drawable.correct).into(layoutCouponBinding.imageChecked)
            pos=-1
        }else{
            Glide.with(context).load(R.drawable.oval).into(layoutCouponBinding.imageChecked)
        }


    }
}