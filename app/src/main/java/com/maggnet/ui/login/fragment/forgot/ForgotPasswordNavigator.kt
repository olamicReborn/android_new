package com.maggnet.ui.login.fragment.forgot

import com.maggnet.ui.base.BaseNavigator

interface ForgotPasswordNavigator : BaseNavigator {

    fun moveToLoginScreen(message : String)
}