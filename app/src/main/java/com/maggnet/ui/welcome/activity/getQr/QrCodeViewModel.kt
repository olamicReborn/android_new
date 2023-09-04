package com.maggnet.ui.welcome.activity.getQr

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.PhoneAuthProvider
import com.maggnet.R
import com.maggnet.data.qrcode.model.QRCodeRequestByImei
import com.maggnet.data.qrcode.model.QRCodeResponseByImei
import com.maggnet.data.qrcode.model.ScanQRCodeResponseByImei
import com.maggnet.data.qrcode.usecase.DeleteScanUserUseCase
import com.maggnet.data.qrcode.usecase.GetQRCodeUseCase
import com.maggnet.data.qrcode.usecase.ScanQRCodeUseCase
import com.maggnet.data.register.login.model.SendOtpRequest
import com.maggnet.data.register.login.usecase.SendOtpUseCase
import com.maggnet.data.register.otp.model.CheckRegisteredUserRequest
import com.maggnet.data.register.otp.model.CheckRegisteredUserResponse
import com.maggnet.data.register.otp.model.ScanUserRequest
import com.maggnet.data.register.otp.model.UserRequest
import com.maggnet.data.register.otp.usecase.CheckRegisteredUserUseCase
import com.maggnet.data.register.otp.usecase.DeleteUserUseCase
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.remote.BaseResponse
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.base.BaseViewModel
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.ApiErrorMessages
import com.maggnet.utils.AppStatus
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val qrCodeUseCase: GetQRCodeUseCase,
    private val scanCodeUseCase: ScanQRCodeUseCase,
    private val deleteUserUseCase: DeleteScanUserUseCase

) : BaseViewModel<QRCodeNavigator>() {

    @Inject
    lateinit var preferenceManager: PreferenceManager


    fun callgetQRcode(imei: String,context: Context) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                qrCodeUseCase.execute(
                    QrcodeUserSubscriber(),
                    GetQRCodeUseCase.Params.create(
                        QRCodeRequestByImei(
                            imei = imei)
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

    fun scangetQRcode(imei: String,context: Context) {
        if(AppStatus.getInstance(context).isOnline) {
          //  getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                scanCodeUseCase.execute(
                    ScanQrcodeUserSubscriber(),
                    ScanQRCodeUseCase.Params.create(
                        QRCodeRequestByImei(
                            imei = imei)
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

    inner class QrcodeUserSubscriber :
        OptimizedCallbackWrapper<QRCodeResponseByImei>() {

        override fun onApiSuccess(response: QRCodeResponseByImei) {
            if (response.success == 1) {
                getNavigator()?.setProgressVisibility(View.GONE)

                response.data.let {
                        getNavigator()?.qrcode(it)
                    }
            } else {
                getNavigator()?.setProgressVisibility(View.GONE)
                getNavigator()?.error(response.message)

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

    inner class ScanQrcodeUserSubscriber :
        OptimizedCallbackWrapper<ScanQRCodeResponseByImei>() {

        override fun onApiSuccess(response: ScanQRCodeResponseByImei) {
            if (response.success == 1) {
               // getNavigator()?.setProgressVisibility(View.GONE)

                response.data.let {
                    getNavigator()?.scanData(userid=it.userid, id = it.id)
                }
            } else {
               // getNavigator()?.setProgressVisibility(View.GONE)
             //   getNavigator()?.error(response.message)

            }
        }

        override fun onApiError(error: BaseError) {
          //  getNavigator()?.setProgressVisibility(View.GONE)
            getNavigator()?.prepareAlert(
                title = R.string.app_error,
                message = ApiErrorMessages.getErrorMessage(error)
            )
        }
    }

    fun deleteScanUserApi(id:String,context:Context) {
        if(AppStatus.getInstance(context).isOnline) {
            getNavigator()?.setProgressVisibility(View.VISIBLE)
            addDisposable(
                deleteUserUseCase.execute(
                    DeleteUserSubscriber(),
                    DeleteScanUserUseCase.Params.create(
                        ScanUserRequest(
                            id = id
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
                getNavigator()?.success(response.message)
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