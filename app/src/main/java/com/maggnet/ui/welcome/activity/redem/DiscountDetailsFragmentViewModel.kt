package com.maggnet.ui.redeem.discount

import android.view.View
import com.google.gson.JsonObject
import com.maggnet.R
import com.maggnet.data.redeem.model.RedeemCouponResponse
import com.maggnet.data.redeem.model.RewardCouponResponse
import com.maggnet.data.redeem.usecase.CouponValidityUseCase
import com.maggnet.data.redeem.usecase.RedeemCouponUseCase
import com.maggnet.data.redeem.usecase.RewardCouponUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class DiscountDetailsFragmentViewModel @Inject constructor(
    private var rewardCouponUseCase: RewardCouponUseCase,
    private var redeemCouponUseCase: RedeemCouponUseCase,
    private var couponValidityUseCase: CouponValidityUseCase
) : BaseViewModel<DiscountDetailsNavigator>() {


    @Inject
    lateinit var preferenceManager: PreferenceManager


    fun callValidCouponApi(rewardCode: String,countryCode: String) {

        var json = JsonObject()
        json.addProperty("customer_mobile_number", preferenceManager.getMobileNumberForRegistration())
        json.addProperty("mobile_country_code", countryCode)
        json.addProperty("reward_code", rewardCode)
        json.addProperty("device_imei", preferenceManager.getIMEINumber())
        var additionalJson = JsonObject()
        additionalJson.addProperty(
            "user_id",
            preferenceManager.getUserId()
        )
        json.add("additional_data", additionalJson)


        addDisposable(
            couponValidityUseCase.execute(
                ValidCouponSubscriber(),
                CouponValidityUseCase.Params.create(
                    RequestBody.create(
                        "application/json; charset=utf-8".toMediaTypeOrNull(),
                        json.toString()
                    )
                )
            )
        )
    }

    inner class ValidCouponSubscriber :
        OptimizedCallbackWrapper<BaseResponse>() {

        override fun onApiSuccess(response: BaseResponse) {
            if (response.success == 0) {
                getNavigator()?.invalidCoupon(response.message)
            }
        }

        override fun onApiError(error: BaseError) {
            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = ApiErrorMessages.getErrorMessage(error)
            )
        }
    }

    fun callRewardCouponApi(rewardCode: String,countryCode: String,amount: Double,invoiceNumber: String) {
        getNavigator()?.setProgressVisibility(View.VISIBLE)

        var json = JsonObject()
            json.addProperty("customer_mobile_number", preferenceManager.getMobileNumberForRegistration())
            json.addProperty("mobile_country_code", countryCode)
            json.addProperty("reward_code", rewardCode)
            json.addProperty("device_imei", "864445040446587")
            json.addProperty("amount", amount)
            json.addProperty("invoice_no",invoiceNumber)
            var additionalJson = JsonObject()
            additionalJson.addProperty(
                "user_id",
               preferenceManager.getUserId()
            )
            json.add("additional_data", additionalJson)

        addDisposable(
            rewardCouponUseCase.execute(
                CouponRewardSubscriber(invoiceNumber),
                RewardCouponUseCase.Params.create(
                    RequestBody.create(
                        "application/json; charset=utf-8".toMediaTypeOrNull(),
                        json.toString()
                    )
                )
            )
        )
    }

    inner class CouponRewardSubscriber(var invoiceNumber: String) :
        OptimizedCallbackWrapper<RewardCouponResponse>() {

        override fun onApiSuccess(response: RewardCouponResponse) {
            getNavigator()?.setProgressVisibility(View.GONE)

            if (response.success == 1) {
                if (response.couponId == "0") {
                    getNavigator()?.moveToInvoiceDetails(
                        response.cartTotal,
                        response.grandTotal,
                        response.invoiceNo,
                        response.couponName,
                        response.userName,
                        response.message,
                        response.couponId
                    )
                } else {
                    getNavigator()?.couponRedeem(
                        response.grandTotal,
                        response.cartTotal,
                        response.userId,
                        response.couponId,
                        response.userName,
                        response.message,
                        response.couponName,
                        invoiceNumber
                    )
                }

            } else {
                getNavigator()?.setProgressVisibility(View.GONE)

                getNavigator()?.prepareAlert(
                    title = R.string.app_error,
                    message = response.message
                )
            }
        }

        override fun onApiError(error: BaseError) {
            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = ApiErrorMessages.getErrorMessage(error)
            )
        }
    }

    fun callRedeemCouponApi(
        cartTotal: Double,
        grandTotal: Double,
        couponId: String,
        userName: String,
        couponName: String,
        invoiceNumber: String
    ) {
        getNavigator()?.setProgressVisibility(View.VISIBLE)

        var json = JsonObject()
        json.addProperty("user_id", preferenceManager.getUserId())
        json.addProperty("coupon_id", couponId)
        json.addProperty("cart_total", cartTotal)
        json.addProperty("grand_total", grandTotal)
        json.addProperty("device_imei","864445040446587")
        json.addProperty("invoice_no", invoiceNumber)
        json.addProperty("coupon_name", couponName)
        addDisposable(
            redeemCouponUseCase.execute(
                CouponRedeemSubscriber(cartTotal, grandTotal, userName),
                RedeemCouponUseCase.Params.create(
                    RequestBody.create(
                        "application/json; charset=utf-8".toMediaTypeOrNull(),
                        json.toString()
                    )
                )
            )
        )
    }

    inner class CouponRedeemSubscriber(
        var cartTotal: Double,
        var grandTotal: Double,
        var userName: String
    ) :
        OptimizedCallbackWrapper<RedeemCouponResponse>() {

        override fun onApiSuccess(response: RedeemCouponResponse) {
            getNavigator()?.setProgressVisibility(View.GONE)

            if (response.success == 1) {
                getNavigator()?.moveToInvoiceDetails(
                    cartTotal,
                    grandTotal,
                    response.invoiceNo,
                    response.couponName,
                    userName,
                    response.message,
                    "1"
                )
            } else {
                getNavigator()?.prepareAlert(
                    title = R.string.app_error,
                    message = response.message
                )
            }
        }

        override fun onApiError(error: BaseError) {
            getNavigator()?.setProgressVisibility(View.GONE)

            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = ApiErrorMessages.getErrorMessage(error)
            )
        }
    }


}