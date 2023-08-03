package com.maggnet.data.redeem.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty


class RewardCouponResponse(
    @SerializedName("cart_total") val cartTotal: Double = 0.0,
    @SerializedName("grand_total") val grandTotal: Double = 0.0,
    @SerializedName("user_id") val userId: String = String.empty,
    @SerializedName("coupon_id") val couponId: String = String.empty,
    @SerializedName("user_name") val userName: String = String.empty,
    @SerializedName("business_address_id") val businessAddressId: String = String.empty,
    @SerializedName("invoice_no") val invoiceNo: String = String.empty,
    @SerializedName("coupon_name") val couponName: String = String.empty
) : BaseResponse()