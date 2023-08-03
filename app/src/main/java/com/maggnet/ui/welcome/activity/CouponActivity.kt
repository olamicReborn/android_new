package com.maggnet.ui.welcome.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maggnet.R
import com.maggnet.data.coupons.model.CouponsListImeiResponse
import com.maggnet.databinding.ActivitySelectCouponBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.redeem.discount.DiscountDetailsFragmentViewModel
import com.maggnet.ui.redeem.discount.DiscountDetailsNavigator
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
    View.OnClickListener,CouponListAdapter.CouponListener,CouponListNavigator,
    DiscountDetailsNavigator {

    private var couponListAdapter:CouponListAdapter? = null

    private val couponViewModel: CouponViewModel by viewModels()
    private var couponsList = java.util.ArrayList<CouponsListImeiResponse.CouponListData>()
    val discountDetailsFragmentViewModel: DiscountDetailsFragmentViewModel by viewModels()

    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var preferenceManager: PreferenceManager
    var carttotal: Double=0.0
    var grandtotal: Double=0.0
    var username: String=""
    var coupon_id: String=""
    var coupon_name: String=""
    var invoice_no: String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}

    override fun initUserInterface() {
        couponViewModel.setNavigator(this)
        discountDetailsFragmentViewModel.setNavigator(this)

        couponViewModel.callCouponListApi("864445040446587",intent.getStringExtra("userid").toString())
        couponListAdapter = CouponListAdapter(this,this@CouponActivity)
        viewDataBinding.couponList.apply {
            linearLayoutManager = LinearLayoutManager(this@CouponActivity, LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            adapter = couponListAdapter

        }


        viewDataBinding.apply {
            name.text=preferenceManager.getUserName()
            btnNext.setOnClickListener(this@CouponActivity)
            backArrow.setOnClickListener(this@CouponActivity)
            amount.text=intent.getStringExtra("amount").toString()+"JOD"

        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    if (grandtotal==0.0){
                        invoice_no= UUID.randomUUID().toString()
                        discountDetailsFragmentViewModel.callRewardCouponApi(
                            "0",intent.getStringExtra("country_code").toString(),intent.getStringExtra("amount").toString().toDouble(),
                            invoice_no
                        )
                    }else {
                        discountDetailsFragmentViewModel.callRedeemCouponApi(
                            carttotal, grandtotal, coupon_id, username, coupon_name, invoice_no
                        )
                    }
                }
                R.id.backArrow->{
                    finish()
                }
                else -> {}
            }
        }
    }

    override fun onItemClick(item: CouponsListImeiResponse.CouponListData) {
        invoice_no= UUID.randomUUID().toString()
        discountDetailsFragmentViewModel.callRewardCouponApi(
            item.id,intent.getStringExtra("country_code").toString(),intent.getStringExtra("amount").toString().toDouble(),
            invoice_no
        )
    }

    override fun setAdapterValues(data: List<CouponsListImeiResponse.CouponListData>) {
        lifecycleScope.launch(Dispatchers.Main) {
            data.let {
                couponsList.addAll(data)
            }
            if (couponsList.isNotEmpty()) {
                viewDataBinding.couponList.visibility = View.VISIBLE
                couponListAdapter?.setItems(couponsList)

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

    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        Toast.makeText(this@CouponActivity,message,Toast.LENGTH_LONG).show()

    }
}