package com.maggnet.ui.redeem.discount

import com.maggnet.ui.base.BaseNavigator
import org.json.JSONObject

interface DiscountDetailsNavigator : BaseNavigator {

    fun couponRedeem(grandTotal : Double, cartTotal : Double, userId : String, couponId : String, userName : String, message : String, couponName: String, invoiceNumber: String)

    fun moveToInvoiceDetails(cartTotal : Double, grandTotal : Double, invoiceNumber : String, couponName : String, userName: String, message: String, couponId: String)

    fun invalidCoupon(message: String)
}