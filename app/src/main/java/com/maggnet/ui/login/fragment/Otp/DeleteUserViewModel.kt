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
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.data.register.otp.usecase.DeleteUserUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.AppStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteUserViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel<ForgotPasswordNavigator>() {


    fun deleteUserApi(userid:String,context:Context) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                deleteUserUseCase.execute(
                    DeleteUserSubscriber(),
                    DeleteUserUseCase.Params.create(
                        UserRequest(
                            user_id = userid
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


    inner class DeleteUserSubscriber :
        OptimizedCallbackWrapper<BaseResponse>() {

        override fun onApiSuccess(response: BaseResponse) {
            getNavigator()?.setProgressVisibility(View.GONE)
            if (response.success == 1) {
                getNavigator()?.movetoHome(response.message)
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