package com.maggnet.ui.welcome.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import com.maggnet.R
import com.maggnet.databinding.ActivityScanQrcodeBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ScanQrActivity : BaseActivity<ActivityScanQrcodeBinding>(ActivityScanQrcodeBinding::inflate),
    View.OnClickListener {

    @Inject
    lateinit var preferenceManager: PreferenceManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}

    override fun initUserInterface() {
        viewDataBinding.apply {
            btnNext.setOnClickListener(this@ScanQrActivity)

        }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnNext ->{
                    startActivity(Intent(this,CouponActivity::class.java))

                }
                else -> {}
            }
        }
    }

    fun getDeviceIMEI(): String? {
        var deviceUniqueIdentifier: String? = null
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (null != tm) {
            deviceUniqueIdentifier = tm.deviceId
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length) {
            deviceUniqueIdentifier =
                Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return deviceUniqueIdentifier
    }
}