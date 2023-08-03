package com.maggnet.ui.welcome.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.maggnet.R
import com.maggnet.databinding.ActivityAmountEntryBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.login.fragment.Otp.SendOtpViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AmountEntryActivity : BaseActivity<ActivityAmountEntryBinding>(ActivityAmountEntryBinding::inflate),
    View.OnClickListener,
    ForgotPasswordNavigator {

    @Inject
    lateinit var preferenceManager: PreferenceManager
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private var imei: String=""
    private var telephonyManager: TelephonyManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendOtpViewModel.setNavigator(this@AmountEntryActivity)
}

    override fun initUserInterface() {
        viewDataBinding.apply {
            btnNext.setOnClickListener(this@AmountEntryActivity)

        }

      //  callVerifyDevice()
    }

    private fun callVerifyDevice() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var imei: String? = ""
        imei = if (Build.VERSION.SDK_INT >= 26) {
            telephonyManager.imei
        } else {
            telephonyManager.deviceId
        }
        if (!TextUtils.isEmpty(imei)) {
            preferenceManager.setIMEINumber(imei)
        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    if(viewDataBinding.edtAmount.text.toString().equals("")){
                        Toast.makeText(this,"Please enter amount",Toast.LENGTH_LONG).show()
                    }else if(!viewDataBinding.edtAmount.text.toString().equals("") && !viewDataBinding.edtPhoneNumber.text.toString().equals("")){
                        sendOtpViewModel.callSendOtpApi(viewDataBinding.countryCodePicker.selectedCountryCode.toString(),viewDataBinding.edtPhoneNumber.text.toString())
                    }else {


                    }
                }
                else -> {}
            }
        }
    }

    override fun moveToLoginScreen(message: String) {
        if(message.equals("OTP sent successfully")){
            startActivity(Intent(this,EnterOtpActivity::class.java)
                .putExtra("country_code",viewDataBinding.countryCodePicker.selectedCountryCode.toString())
                .putExtra("phone_no",viewDataBinding.edtPhoneNumber.text.toString())
                .putExtra("amount",viewDataBinding.edtAmount.text.toString()))
        }else{

        }

    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressBar.visibility=visibility
    }

    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

    }
}