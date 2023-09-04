package com.maggnet.ui.welcome.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maggnet.R
import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.databinding.ActivitySelectCouponBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.login.fragment.Otp.DeleteUserViewModel
import com.maggnet.ui.login.fragment.Otp.SendOtpViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.ui.redeem.discount.DiscountDetailsFragmentViewModel
import com.maggnet.ui.redeem.discount.DiscountDetailsNavigator
import com.maggnet.ui.register.fragment.verification.OtpVerificationNavigator
import com.maggnet.ui.welcome.activity.coupon.CouponListAdapter
import com.maggnet.ui.welcome.activity.coupon.CouponListNavigator
import com.maggnet.ui.welcome.activity.coupon.CouponViewModel
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@AndroidEntryPoint
class CouponActivity : BaseActivity<ActivitySelectCouponBinding>(ActivitySelectCouponBinding::inflate),
    View.OnClickListener,CouponListAdapter.CouponListener,CouponListNavigator,ForgotPasswordNavigator,
    DiscountDetailsNavigator {

    private var couponListAdapter:CouponListAdapter? = null

    private val couponViewModel: CouponViewModel by viewModels()
    private var couponsList = java.util.ArrayList<CouponsListImeiResponse.CouponListData>()
    val discountDetailsFragmentViewModel: DiscountDetailsFragmentViewModel by viewModels()
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private val deleteUserViewModel: DeleteUserViewModel by viewModels()

    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var preferenceManager: PreferenceManager
    var carttotal: Double=0.0
    var grandtotal: Double=0.0
    var username: String=""
    var coupon_id: String=""
    var coupon_name: String=""
    var invoice_no: String=""

    var phone_no:String=""
    var tamount:String=""
    var country_code:String=""
    var itemId="0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}

    override fun initUserInterface() {
        couponViewModel.setNavigator(this)
        discountDetailsFragmentViewModel.setNavigator(this)
        sendOtpViewModel.setNavigator(this)
        deleteUserViewModel.setNavigator(this@CouponActivity)


        couponViewModel.callCouponListApi(preferenceManager.getIMEINumber().toString(),preferenceManager.getUserId().toString(),this)
        couponListAdapter = CouponListAdapter(this,this@CouponActivity)
        viewDataBinding.couponList.apply {
            linearLayoutManager = LinearLayoutManager(this@CouponActivity, LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            adapter = couponListAdapter

        }

        phone_no=preferenceManager.getMobileNumberForRegistration()
        tamount=preferenceManager.getUserAmount()
        country_code=preferenceManager.getUserCountryCode()

        viewDataBinding.apply {
            if(!preferenceManager.getUserName().equals("")){
                name.text=preferenceManager.getUserName()
            }else{
                name.visibility= GONE
            }
            btnNext.setOnClickListener(this@CouponActivity)
            backArrow.setOnClickListener(this@CouponActivity)
            amount.text=tamount+"JOD"

        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    invoice_no= UUID.randomUUID().toString()

                    if (itemId.equals("0")){
                        discountDetailsFragmentViewModel.callRewardCouponApi(
                            "0",preferenceManager.getUserCountryCode(),preferenceManager.getUserAmount().toDouble(),
                            invoice_no,this
                        )
                    }else {
                        if(preferenceManager.getOtpRequested()){
                            sendOtpViewModel.callSendOtpApi(country_code,phone_no,this)
                        }else{
                            discountDetailsFragmentViewModel.callRewardCouponApi(
                                itemId,country_code,tamount.toDouble(),
                                invoice_no,this)

                        }
                    }
                }
                R.id.backArrow->{
                    preferenceManager.clearSharedPreferences()
                   startActivity(Intent(this,AmountEntryActivity::class.java))
                }
                else -> {}
            }
        }
    }

    override fun onItemClick(item: CouponsListImeiResponse.CouponListData) {

        itemId=item.id

    }

    override fun onRestart() {
        super.onRestart()
   /*     invoice_no= UUID.randomUUID().toString()
        discountDetailsFragmentViewModel.callRewardCouponApi(
            item.id,country_code,tamount.toDouble(),
            invoice_no
        )*/
    }

    override fun setAdapterValues(data: List<CouponsListImeiResponse.CouponListData>) {
        lifecycleScope.launch(Dispatchers.Main) {
            data.let {
                couponsList.addAll(data)
            }
            if (couponsList.isNotEmpty()) {
                viewDataBinding.couponList.visibility = View.VISIBLE
                couponListAdapter?.setItems(couponsList)
            }else{
                Toast.makeText(this@CouponActivity,"No Coupon Available",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun displayMessage(message: String) {

    }

    override fun couponRedeem(
        grandTotal: Double,
        cartTotal: Double,
        userId: String,
        couponId: String,
        userName: String,
        message: String,
        couponName: String,
        invoiceNumber: String
    ) {
        if(message.equals("Invalid Coupon")){
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }else{
            carttotal=cartTotal
            grandtotal=grandTotal
            coupon_id=couponId
            username=userName
            coupon_name=couponName
            viewDataBinding.view.visibility=VISIBLE
            viewDataBinding.totalDiscount.visibility=VISIBLE
            viewDataBinding.grandTotal.text=grandTotal.toString()+"JOD"


            discountDetailsFragmentViewModel.callRedeemCouponApi(
                carttotal, grandtotal, coupon_id, username, coupon_name, invoice_no,this
            )
        }
    }

    override fun moveToInvoiceDetails(
        cartTotal: Double,
        grandTotal: Double,
        invoiceNumber: String,
        couponName: String,
        userName: String,
        message: String,
        couponId: String
    ) {
        startActivity(Intent(this,SuccessActivity::class.java)
            .putExtra("userName",userName)
            .putExtra("grandTotal",grandTotal.toString())
            .putExtra("cartTotal",cartTotal.toString()))
    }

    override fun invalidCoupon(message: String) {
    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressBar.visibility=visibility
    }

    override fun moveToLoginScreen(message: String) {
        if(message.equals("OTP sent successfully")){
            startActivity(Intent(this,EnterOtpActivity::class.java).putExtra("itemid",itemId))
        }else if(message.equals("Invalid mobile number")){
            deleteUserViewModel.deleteUserApi(preferenceManager.getUserId().toString(),this)
        }

    }

    override fun movetoHome(message: String) {
        startActivity(Intent(this,AmountEntryActivity::class.java))

    }

    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        super<DiscountDetailsNavigator>.prepareAlert(title, messageResourceId, message)
        super<ForgotPasswordNavigator>.prepareAlert(title, messageResourceId, message)
        super<CouponListNavigator>.prepareAlert(title, messageResourceId, message)
        if(message.equals("Device not registered")){
            preferenceManager.clearSharedPreferences()
            startActivity(Intent(this,AmountEntryActivity::class.java))
        }
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

    }
}