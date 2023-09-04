package com.maggnet.ui.home.fragment.qrcode.scan

import com.maggnet.ui.base.BaseNavigator

interface QRCodeScanNavigator : BaseNavigator {
    fun redirectUserToPreviousScreen(errorMessage: Int)
    fun checkScanResult(uriString: String)
}