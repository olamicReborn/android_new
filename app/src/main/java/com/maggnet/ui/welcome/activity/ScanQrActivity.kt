package com.maggnet.ui.welcome.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.maggnet.R
import com.maggnet.databinding.ActivityScanQrcodeBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.home.fragment.qrcode.scan.QRCodeScannerActivity
import com.maggnet.ui.welcome.activity.getQr.QRCodeNavigator
import com.maggnet.ui.welcome.activity.getQr.QrCodeViewModel
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ScanQrActivity : BaseActivity<ActivityScanQrcodeBinding>(ActivityScanQrcodeBinding::inflate),
    View.OnClickListener, QRCodeNavigator {

    @Inject
    lateinit var preferenceManager: PreferenceManager
    private val qrcodeViewModel: QrCodeViewModel by viewModels()
    var handler = Handler()
    var runnable: Runnable? = null
    var delay = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun setOTPWaitingCounter() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewDataBinding.code.visibility = View.VISIBLE
                viewDataBinding.code.text = "Timer - ${millisUntilFinished / 1000} sec"
            }

            override fun onFinish() {
                viewDataBinding.code.visibility = View.GONE
                finish()
            }
        }.start()
    }


    override fun initUserInterface() {
        qrcodeViewModel.setNavigator(this@ScanQrActivity)
        qrcodeViewModel.callgetQRcode(preferenceManager.getIMEINumber().toString(), this)
        viewDataBinding.apply {
            //   btnNext.setOnClickListener(this@ScanQrActivity)
            scanQr.setOnClickListener(this@ScanQrActivity)
            backArrow.setOnClickListener(this@ScanQrActivity)
            amount.text = intent.getStringExtra("amount") + " JOD"
        }
        setOTPWaitingCounter()

    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.backArrow -> {
                    finish()
                }

                R.id.scan_qr -> {
                    startActivity(Intent(this, QRCodeScannerActivity::class.java))
                }

                else -> {}
            }
        }
    }

    override fun qrcode(qr: String) {
        try {
            val cleanImage: String =
                qr.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "")
            val decodedString: ByteArray = Base64.decode(cleanImage, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            Glide.with(this).load(decodedByte).fitCenter().into(viewDataBinding.imageQr);
        } catch (e: NullPointerException) {
            //do nothing for now
        }

    }

    override fun error(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun scanData(userid: String,id:String) {
        preferenceManager.setUserId(userid)
        preferenceManager.setOtpRequested(false)
        handler.removeCallbacks(runnable!!)
        qrcodeViewModel.deleteScanUserApi(id, this)
    }

    override fun success(message: String) {
        val openActivityIntent = Intent(
            this,
            CouponActivity::class.java
        )
        startActivity(openActivityIntent)

    }

    override fun onRestart() {
        super.onRestart()
        viewDataBinding.progressBar.visibility = View.GONE
    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressBar.visibility = visibility
    }


    override fun prepareAlert(title: Int, messageResourceId: Int, message: String) {
        super.prepareAlert(title, messageResourceId, message)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            performGetStatus()
        }.also { runnable = it }, delay.toLong())
        super.onResume()

    }

    private fun performGetStatus() {
        qrcodeViewModel.scangetQRcode(preferenceManager.getIMEINumber().toString(), this)
    }

    override fun onPause() {
        handler.removeCallbacks(runnable!!) //stop handler when activity not visible super.onPause();
        super.onPause()
    }
}