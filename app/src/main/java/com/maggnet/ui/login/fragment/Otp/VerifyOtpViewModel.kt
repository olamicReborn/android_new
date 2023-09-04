package com.maggnet.ui.login.fragment.Otp

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.maggnet.R
import com.maggnet.data.register.login.model.ForgotPasswordResponse
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.model.VerifyOtpRequest
import com.maggnet.data.register.login.usecase.SendOtpUseCase
import com.maggnet.data.register.login.usecase.VerifyOtpUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.AppStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : BaseViewModel<ForgotPasswordNavigator>() {

    val otpChar1 = MutableLiveData<String?>()
    val otpChar2 = MutableLiveData<String?>()
    val otpChar3 = MutableLiveData<String?>()
    val otpChar4 = MutableLiveData<String?>()
    val otpChar5 = MutableLiveData<String?>()
    val otpChar6 = MutableLiveData<String?>()


    fun callVerifyOtpApi(countryCode: String,phoneNumber: String,otp:String,context:Context) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                verifyOtpUseCase.execute(
                    SendOtpSubscriber(),
                    VerifyOtpUseCase.Params.create(
                        VerifyOtpRequest(
                            country_code = countryCode,
                            phone_number = phoneNumber,
                            otp = otp
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