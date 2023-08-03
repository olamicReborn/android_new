package com.maggnet.ui.register.fragment.verification

import android.app.Activity
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.PhoneAuthProvider
import com.maggnet.R
import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.usecase.CheckRegisteredUserUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val checkRegisteredUserUseCase: CheckRegisteredUserUseCase
) : BaseViewModel<OtpVerificationNavigator>() {

    //region VARIABLE
    var countDownTimer: CountDownTimer? = null

    @Inject
    lateinit var preferenceManager: PreferenceManager


    //region OTP VERIFICATION API
    fun callCheckRegisteredUserApi(phoneNumberWithCountryCode: String,countrycode:String) {
        getNavigator()?.setProgressVisibility(View.VISIBLE)
        addDisposable(
            checkRegisteredUserUseCase.execute(
                CheckRegisteredUserSubscriber(),
                CheckRegisteredUserUseCase.Params.create(
                    CheckRegisteredUserRequest(
                        phoneNumber = phoneNumberWithCountryCode,
                        country_code = countrycode
                    )
                )
            )
        )
    }

    inner class CheckRegisteredUserSubscriber :
        OptimizedCallbackWrapper<CheckRegisteredUserResponse>() {

        override fun onApiSuccess(response: CheckRegisteredUserResponse) {
            if (response.success == 1) {
                    response.data.let {
                        preferenceManager.run {
                            setUserId(it.userId)
                            setToken(it.token)
                            if (!TextUtils.isEmpty(it.qrCode)) {
                                setQRCode(it.qrCode)
                            }
                            if (!TextUtils.isEmpty(it.email)) {
                                setUserEmail(it.email)
                            }
                            if (!TextUtils.isEmpty(it.profile)) {
                                setUserImage(it.profile)
                            }
                            if (!TextUtils.isEmpty(it.name)) {
                                setUserName(it.name)
                            }
                            if (!TextUtils.isEmpty(it.phone_number)) {
                                setMobileNumberForRegistration(it.phone_number)
                            }
                        }
                        getNavigator()?.loginUser(it.userId)
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
            getNavigator()?.setProgressVisibility(View.GONE)
            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = ApiErrorMessages.getErrorMessage(error)
            )
        }
    }



}