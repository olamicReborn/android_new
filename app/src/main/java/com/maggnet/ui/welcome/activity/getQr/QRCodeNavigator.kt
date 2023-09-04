package com.maggnet.ui.welcome.activity.getQr

import com.maggnet.ui.base.BaseNavigator

interface QRCodeNavigator : BaseNavigator {

    fun qrcode(qr : String)
    fun error(error : String)
    fun scanData(userid : String,id:String)
    fun success(message : String)


}