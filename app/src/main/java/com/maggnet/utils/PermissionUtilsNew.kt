package com.maggnet.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Process
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.utils.Utils.isAndroidRAndAbove

import java.util.*

object PermissionUtilsNew {

    const val CAMERA_PERMISSION_CODE = 1212
    const val STORAGE_PERMISSION_CODE = 1313


    /**
     *  Checks if app has been granted READ_EXTERNAL_STORAGE or WRITE_EXTERNAL_STORAGE permission
     *  @return true , if granted
     * **/
    fun hasStoragePermission(): Boolean {
        return if (isAndroidRAndAbove()) {
            ContextCompat.checkSelfPermission(
                MaggnetApplication.instance,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                MaggnetApplication.instance,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     *  Checks if app has been granted CAMERA permission
     *  @return true , if granted
     * **/
    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            MaggnetApplication.instance,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Shows dialog when rational permission tries exhausted and user needs to enable permission
     * from settings
     * **/
    fun showPermissionSettingsDialog(
        activity: Activity,
        listener: DialogManager.AlertDialogListener? = null
    ) {
        DialogManager().twoButtonDialog(
            context = activity,
            title = activity.getString(R.string.permission),
            message = activity.getString(R.string.grant_permission_from_settings),
            spannedMessage = false,
            positiveButtonText = activity.getString(R.string.open_settings),
            alertDialogListener = object : DialogManager.AlertDialogListener {
                override fun onPositiveButtonClicked() {
                    activity.let {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + it.packageName)
                        activity.startActivity(intent)
                        listener?.onPositiveButtonClicked()
                    }
                }

                override fun onNegativeButtonClicked() {
                    super.onNegativeButtonClicked()
                    listener?.onNegativeButtonClicked()
                }
            }
        )
    }





}