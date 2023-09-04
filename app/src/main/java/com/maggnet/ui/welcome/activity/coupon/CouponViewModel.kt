package com.maggnet.ui.welcome.activity.coupon


import android.content.Context
import android.view.View
import com.maggnet.R
import com.maggnet.data.coupons.model.*
import com.maggnet.data.coupons.usecase.*
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.usecase.SendOtpUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.extensions.empty
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.AppStatus
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CouponViewModel @Inject constructor(
    private var couponListUseCase: CouponListUseCase
) : BaseViewModel<CouponListNavigator>() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    fun callCouponListApi(
        imei: String,userid:String,context: Context
    ) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                couponListUseCase.execute(
                    CouponListSubscriber(),
                    CouponListUseCase.Params.create(
                        CouponsListImeiRequest(
                            imei =imei,
                            userid = userid
                        )
                    )
                )
            )
        }else{
            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = "No Internet available"
            )
        }

    }



    inner class CouponListSubscriber :
        OptimizedCallbackWrapper<CouponsListImeiResponse>() {

        override fun onApiSuccess(response: CouponsListImeiResponse) {
            getNavigator()?.setProgressVisibility(View.GONE)
            if (response.success == 1) {
                response.data?.let {
                    getNavigator()?.setAdapterValues(it)
                }
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