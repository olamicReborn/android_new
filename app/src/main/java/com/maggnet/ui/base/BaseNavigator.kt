package com.maggnet.ui.base

import com.maggnet.ui.extensions.empty


interface BaseNavigator {

    fun prepareAlert(title: Int, messageResourceId: Int = 0, message: String = String.empty) {}

    fun setProgressVisibility(visibility: Int) {}

    fun moveToHome(referralDiscount : String){}


}