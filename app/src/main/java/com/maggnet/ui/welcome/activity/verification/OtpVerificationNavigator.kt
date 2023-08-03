package com.maggnet.ui.register.fragment.verification

import com.maggnet.ui.base.BaseNavigator

interface OtpVerificationNavigator : BaseNavigator {

    fun updateWrongOtpStatus()

    fun updateSuccessOtpStatus() {}

    fun registerUser()

    fun loginUser(userId : String)

//    fun moveToHome()

}