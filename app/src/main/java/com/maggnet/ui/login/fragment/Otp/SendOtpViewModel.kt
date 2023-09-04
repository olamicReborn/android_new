package com.maggnet.ui.login.fragment.Otp

import android.content.Context
import android.view.View
import com.maggnet.R
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.usecase.SendOtpUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.AppStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendOtpViewModel @Inject constructor(
    private val sendOtpUseCase : SendOtpUseCase
) : BaseViewModel<ForgotPasswordNavigator>() {



    fun callSendOtpApi(countryCode: String, phoneNumber: String, context: Context) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                sendOtpUseCase.execute(
                    SendOtpSubscriber(),
                    SendOtpUseCase.Params.create(
                        SendOtpRequest(
                            country_code = countryCode,
                            phone_number = phoneNumber
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


    inner class SendOtpSubscriber :
        OptimizedCallbackWrapper<ForgotPasswordResponse>() {

        override fun onApiSuccess(response: ForgotPasswordResponse) {
            getNavigator()?.setProgressVisibility(View.GONE)
            if (response.success == 1) {
                getNavigator()?.moveToLoginScreen(response.message)
            } else if(response.success ==0 && response.message.equals("Invalid mobile number")) {
                getNavigator()?.moveToLoginScreen(response.message)
                getNavigator()?.prepareAlert(
                    title = R.string.app_error,
                    message = response.message
                )
            } else{
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