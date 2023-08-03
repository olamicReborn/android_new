package com.maggnet.data.coupons.model

import com.google.gson.annotations.SerializedName
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.ui.extensions.empty
import java.io.Serializable

class ShopDetailResponse(@SerializedName("data") val data: ShopDetailData = ShopDetailData()) :
    BaseResponse() {


    class ShopDetailData {

        @SerializedName("category")
        val category: String = String.empty

        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("email")
        val email: String = String.empty

        @SerializedName("about")
        val about: String = String.empty

        @SerializedName("shop_logo")
        val shop_logo: String = String.empty

        @SerializedName("shop_cover")
        val shop_cover: String = String.empty

        @SerializedName("BusinessAddresses")
        val businessAddresses: List<BusinessAddresses>? = null

        @SerializedName("BusinessContactPeople")
        val businessContactPeople: List<BusinessContactPeople>? = null

        @SerializedName("promotions")
        val promotions: List<Promotions>? = null

        @SerializedName("BusinessMedia")
        val businessMedia: List<BusinessMedia>? = null

        @SerializedName("BusinessHours")
        val businessHours: List<BusinessHours>? = null

        @SerializedName("loyalty_reward")
        val loyaltyReward: LoyaltyReward? = null

        @SerializedName("reward_points")
        val rewardPoints: String = String.empty
    }

    class BusinessAddresses : Serializable {

        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("city")
        val city: String = String.empty

        @SerializedName("state")
        val state: String = String.empty

        @SerializedName("country")
        val country: String = String.empty

        @SerializedName("google_address")
        val google_address: String = String.empty

        @SerializedName("lat")
        val lat: String = String.empty

        @SerializedName("long")
        val long: String = String.empty

        var type = 1

        @SerializedName("BusinessAddressPhoneNumbers")
        val businessContactPeople: List<BusinessContactPeople>? = null
    }

    class BusinessContactPeople : Serializable {

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("country_code")
        val country_code: String = String.empty

        @SerializedName("phone_number")
        val phone_number: String = String.empty

        @SerializedName("email")
        val email: String = String.empty
    }

    class Promotions {

        @SerializedName("id")
        val id: String = String.empty

        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("description")
        val description: String = String.empty

        @SerializedName("end_date")
        val end_date: String = String.empty
    }

    class BusinessMedia : Serializable {
        @SerializedName("shop_photo")
        val shopPhoto: String = String.empty
    }

    class BusinessHours {
        @SerializedName("day")
        val day: String = String.empty

        @SerializedName("start_time")
        val startTime: String = String.empty

        @SerializedName("end_time")
        val endTime: String = String.empty

        @SerializedName("is_closed")
        val isClosed: String = String.empty
    }

    class LoyaltyReward {
        @SerializedName("need_to_get_points")
        val needToGetPoints: NeedToGetPoints? = null

        @SerializedName("rewards_on_amount_spent")
        val rewardsOnAmountSpent: String = String.empty

        @SerializedName("loyalties")
        val loyalties = arrayListOf<Loyalties>()

        @SerializedName("loyalty")
        val loyalty: Loyalty? = null
    }

    class NeedToGetPoints {
        @SerializedName("value")
        val value: String = String.empty
    }

    class Loyalty {
        @SerializedName("name")
        val name: String = String.empty

        @SerializedName("loyalty_min_rewards")
        val loyaltyMinRewards: String = String.empty
    }

    class Loyalties {

        @SerializedName("name")
        var name: String = String.empty

        @SerializedName("loyalty_min_rewards")
        var loyaltyMinRewards: String = String.empty

        var rewardsOnAmountSpent: String = String.empty

        var myPoints: String = String.empty
    }

}