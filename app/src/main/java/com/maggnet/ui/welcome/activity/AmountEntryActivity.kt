package com.maggnet.ui.welcome.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaDrm
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.maggnet.R
import com.maggnet.databinding.ActivityAmountEntryBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.login.fragment.Otp.SendOtpViewModel
import com.maggnet.ui.login.fragment.forgot.ForgotPasswordNavigator
import com.maggnet.ui.register.fragment.verification.OtpVerificationNavigator
import com.maggnet.ui.register.fragment.verification.OtpVerificationViewModel
import com.maggnet.utils.AppStatus
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject


@AndroidEntryPoint
class AmountEntryActivity : BaseActivity<ActivityAmountEntryBinding>(ActivityAmountEntryBinding::inflate),
    View.OnClickListener,
    ForgotPasswordNavigator, OtpVerificationNavigator {

    @Inject
    lateinit var preferenceManager: PreferenceManager
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private var imei: String=""
    private val REQUEST_CODE = 101
    private var telephonyManager: TelephonyManager? = null

    private val otpVerificationFragmentViewModel: OtpVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (AppStatus.getInstance(this).isOnline) {
            sendOtpViewModel.setNavigator(this@AmountEntryActivity)
            otpVerificationFragmentViewModel.setNavigator(this)
        } else {
            Toast.makeText(this,"No Internet available",Toast.LENGTH_LONG).show()
       }

            FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    // Get new FCM registration token
                    val token = task.result
                    token?.let {
                        Log.e("token ---->>", it)
                        preferenceManager.setFCMToken(it)
                    }
                })

            // notify user you are online


    }


    override fun onResume() {
        super.onResume()

    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // if permissions are not provided we are requesting for permissions.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                REQUEST_CODE
            )
        } else {
            callVerifyDevice()
        }
    }

    private fun callVerifyDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imei = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            telephonyManager?.let {
                imei = it.imei
            }
        }
        if (!TextUtils.isEmpty(imei)) {
            preferenceManager.setIMEINumber(imei)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            // in the below line, we are checking if permission is granted.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if permissions are granted we are displaying below toast message.
                callVerifyDevice()
            } else {
                // in the below line, we are displaying toast message if permissions are not granted.
                finish()
                //PermissionUtilsNew.showPermissionSettingsDialog(this, this)
            }
        }
    }


    fun getUniqueId(): String? {

        val WIDEVINE_UUID = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
        var wvDrm: MediaDrm? = null
        try {
            wvDrm = MediaDrm(WIDEVINE_UUID)
            val widevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
            val md = MessageDigest.getInstance("SHA-256")
            md.update(widevineId)
            return  md.digest().toHexString()
        } catch (e: Exception) {
            //WIDEVINE is not available
            return null
        }
    }


    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }




    override fun initUserInterface() {
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        checkPermission()

        viewDataBinding.apply {
            btnNext.setOnClickListener(this@AmountEntryActivity)
            edtAmount.setText(preferenceManager.getUserAmount())
            edtPhoneNumber.setText(preferenceManager.getMobileNumberForRegistration())
        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    preferenceManager.setUserAmount("")
                    preferenceManager.setMobileNumberForRegistration("")
                    preferenceManager.setUserCountryCode("")
                    if(viewDataBinding.edtAmount.text.toString().equals("")){
                        Toast.makeText(this,"Please enter amount",Toast.LENGTH_LONG).show()
                    }else if(!viewDataBinding.edtAmount.text.toString().equals("") && !viewDataBinding.edtPhoneNumber.text.toString().equals("")){
                        if(viewDataBinding.edtPhoneNumber.text.toString().length>7){
                            preferenceManager.setUserAmount(viewDataBinding.edtAmount.text.toString())
                            preferenceManager.setOtpRequested(true)
                            otpVerificationFragmentViewModel.callCheckRegisteredUserApi(
                                viewDataBinding.edtPhoneNumber.text.toString(),viewDataBinding.countryCodePicker.selectedCountryCode.toString(),this
                            )
                        }else{
                            Toast.makeText(this,"Enter valid phone number",Toast.LENGTH_LONG).show()
                        }
                      //  sendOtpViewModel.callSendOtpApi(viewDataBinding.countryCodePicker.selectedCountryCode.toString(),viewDataBinding.edtPhoneNumber.text.toString())
                    }else {
                        preferenceManager.setUserAmount(viewDataBinding.edtAmount.text.toString())

                        startActivity(Intent(this,ScanQrActivity::class.java)
                            .putExtra("amount",viewDataBinding.edtAmount.text.toString()))
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

    override fun movetoHome(message: String) {

    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressBar.visibility=visibility
    }

    override fun updateWrongOtpStatus() {
    }

    override fun registerUser() {
    }

    override fun loginUser(userId: String) {
        preferenceManager.setUserCountryCode(viewDataBinding.countryCodePicker.selectedCountryCode.toString())
        startActivity(Intent(this,CouponActivity::class.java))
    }

    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        super<OtpVerificationNavigator>.prepareAlert(title, messageResourceId, message)
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

    }

    override fun onRestart() {
        super.onRestart()
        viewDataBinding.progressBar.visibility= View.GONE
    }
}