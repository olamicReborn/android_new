package com.maggnet.ui.home.fragment.qrcode.scan

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.databinding.ActivityQrCodeScannerBinding
import com.maggnet.ui.base.BaseActivity
import com.maggnet.ui.base.BaseFragment
import com.maggnet.ui.redeem.discount.DiscountDetailsFragmentViewModel
import com.maggnet.ui.redeem.discount.DiscountDetailsNavigator
import com.maggnet.ui.welcome.activity.CouponActivity
import com.maggnet.utils.*
import com.maggnet.utils.codescanner.CodeScanner
import com.maggnet.utils.codescanner.DecodeCallback
import com.maggnet.utils.codescanner.ErrorCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult

@AndroidEntryPoint
class QRCodeScannerActivity :
    BaseActivity<ActivityQrCodeScannerBinding>(ActivityQrCodeScannerBinding::inflate),
    QRCodeScanNavigator, View.OnClickListener {


    // region VARIABLES
    companion object {
        const val USER_ID = "a"
        const val TIME_STAMP = "b"
        const val GALLERY_REQUEST = 1000
        const val SETTINGS_REQUEST = 100
        const val PERMISSION_REQUEST_CODE = 100

    }

    val viewModel: QRCodeScannerViewModel by viewModels()
    @Inject
    lateinit var preferenceManager: PreferenceManager
    private var qrCodeScanner: CodeScanner? = null
    var scanDataObject: JSONObject? = null

    //endregion VARIABLES

    //region LIFECYCLE

    override fun initUserInterface() {
        viewModel.setNavigator(this)

        if (checkPermission()) {
            initViewsAfterPermission()
        } else {
            requestPermission();
        }
        viewDataBinding.barcodeScanner.decodeContinuous(barcodeCallBack)
        viewDataBinding.backArrow.setOnClickListener(this@QRCodeScannerActivity)

    }

    private val barcodeCallBack: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            if (!result.toString().isEmpty()) {
                viewDataBinding.barcodeScanner.pause()
                scanResult(result.toString())
            }
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            //do nothing
        }

    }
    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.backArrow ->{
                    finish()
                }

                else -> {}
            }
        }
    }


    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun initViewsAfterPermission() {
        viewDataBinding.barcodeScanner.setStatusText("")
    }

    /**
     * Open Dialog box to allow or deny
     * the camera permission for scanner
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                initViewsAfterPermission()
            }
            else -> {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            "You need to allow access permissions"
                        ) { _, _ ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null).create().show()
    }


    override fun onResume() {
        super.onResume()
        viewDataBinding.barcodeScanner.resume()
    }


    override fun onPause() {
        super.onPause()
        viewDataBinding.barcodeScanner.pause()

    }


    /*override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.backArrow ->{
                    finish()
                }
                else -> {}
            }
        }
    }*/


    /**
     *  Starting camera preview again in onResume of fragment
     * **/



    override fun redirectUserToPreviousScreen(errorMessage: Int) {

    }

    override fun checkScanResult(uriString: String) {
    }

     fun scanResult(uriString: String) {
        scanDataObject = JSONObject(uriString)

        if (scanDataObject != null) {
            preferenceManager.setOtpRequested(false)

            if (scanDataObject!!.has("user_id")) {
                preferenceManager.setUserId(scanDataObject!!.get("user_id").toString())
                preferenceManager.setUserCountryCode(scanDataObject!!.get("country_code").toString())
                preferenceManager.setMobileNumberForRegistration(scanDataObject!!.get("phone_number").toString())

                startActivity(Intent(this, CouponActivity::class.java))

            }else{
                if(scanDataObject!!.has("additional_data")){
                    preferenceManager.setUserId((scanDataObject?.get("additional_data") as JSONObject).get("user_id").toString())
                    preferenceManager.setUserCountryCode(scanDataObject!!.get("mobile_country_code").toString())
                    preferenceManager.setMobileNumberForRegistration(scanDataObject!!.get("customer_mobile_number").toString())
                    preferenceManager.setUserName(scanDataObject!!.get("customer_name").toString())

                    startActivity(Intent(this, CouponActivity::class.java))

                }

            }
        }
        //  Toast.makeText(this, uriString, Toast.LENGTH_SHORT).show()
    }

    //region PRIVATE




}