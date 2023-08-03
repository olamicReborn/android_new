package com.maggnet.data.coupons.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty
import org.json.JSONObject

class CouponsListImeiResponse(@SerializedName("data") val data: List<CouponListData>? = null) :
    BaseResponse() {


    data class CouponListData(
        var id: String = String.empty,
        var name: String = String.empty,
        var arabic_name: String = String.empty,
        var description: String = String.empty,
        var loyalty_cost: String = String.empty,
        var loyalty_min_rewards: String = String.empty,
        var business_id: String = String.empty,
        var business_address_id: String = String.empty,
        var min_invites: String = String.empty,
        var user_type: String = String.empty,
        var category_id: String = String.empty,
        var lat: String = String.empty,
        var lng: String = String.empty,
        var for_order: String = String.empty,
        var type: String = String.empty,
        var discount_type: String = String.empty,
        var discount: String = String.empty,
        var max_discount_amount: String = String.empty,
        var cost_of_y: String = String.empty,
        var min_invoice_total: String = String.empty,
        var discount_percentage: String = String.empty,
        var required_min_amount: String = String.empty,
        var max_use: String = String.empty,
        var is_linked_with_loyality: String = String.empty,
        var is_trending: String = String.empty,
        var is_featured: String = String.empty,
        var available_for: String = String.empty,
        var valid_type: String = String.empty,
        var sharing_reserved_days: String = String.empty,
        var active_for_days: String = String.empty,
        var total_count: String = String.empty,
        var redeemed_count: String = String.empty,
        var published_on: String = String.empty,
        var status: String = String.empty,
        var start_date: String = String.empty,
        var end_date: String = String.empty,
        var start_time: String = String.empty,
        var end_time: String = String.empty,
        var is_multiplication: String = String.empty,
        var qr_code: String = String.empty,
        var createdAt: String = String.empty,
        var updatedAt: String = String.empty,

    )

}