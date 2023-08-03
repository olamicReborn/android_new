package com.maggnet.data.redeem.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty

class RedeemCouponResponse(
    @SerializedName("invoice_no") val invoiceNo: String =  String.empty,
    @SerializedName("coupon_name") val couponName: String = String.empty
) : BaseResponse() {

}