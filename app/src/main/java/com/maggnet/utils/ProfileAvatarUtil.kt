package com.maggnet.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.maggnet.MaggnetApplication
import com.maggnet.R
import java.io.File


private var TAG = "ProfileAvatarUtil"
const val REQUEST_CODE_SELECT_PIC: Int = 101
const val REQUEST_CODE_SELECT_CAMERA: Int = 102


enum class CHOOSER {
    CAMERA,
    GALLERY
}

fun showCameraAndGalleryOption(
    activity: Activity,
    dialogManager: DialogManager,
    fragment: Fragment
) {
    dialogManager.twoItemDialog(
        activity,
        activity.getString(R.string.gallery),
        activity.getString(R.string.camera),
        object : DialogManager.AlertDialogItemClickListener {
            override fun onItemClicked(which: Int) {
                when (which) {
                    0 -> checkPermission(activity, CHOOSER.GALLERY, fragment)
                    1 -> checkPermission(activity, CHOOSER.CAMERA, fragment)
                }
            }
        })
}


fun checkPermission(activity: Activity, item: CHOOSER, fragment: Fragment) {
    when (item) {

        CHOOSER.CAMERA -> {
            if (PermissionUtilsNew.hasCameraPermission()) {
                openCamera(fragment)
            } else {
                checkListOfPermission(activity, listOf(Manifest.permission.CAMERA), item, fragment)
            }
        }
        CHOOSER.GALLERY -> {
            if (PermissionUtilsNew.hasStoragePermission()) {
                openAlbum(fragment)
            } else {
                if (Utils.isAndroidRAndAbove()) {
                    checkListOfPermission(
                        activity,
                        listOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        item,
                        fragment
                    )
                } else {
                    checkListOfPermission(
                        activity,
                        listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        item,
                        fragment
                    )
                }
            }
        }
    }
}


private fun openAlbum(fragment: Fragment) {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    val mimeTypes = arrayOf("image/*")
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    intent.type = "image/*"
    if (fragment.isAdded) {
        fragment.startActivityForResult(intent, REQUEST_CODE_SELECT_PIC)
    }
}

fun getCameraOutputUri(context: Context): Uri? {
    val file = File(
        MaggnetApplication.instance.applicationContext.externalCacheDir,
        "PROFILE_NAME"
    )
    var uri: Uri? = null
    try {
        uri = FileProvider.getUriForFile(context, context.packageName + ".files", file)
    } catch (e: IllegalArgumentException) {
        AppLogger.e("ProfileSet", "IllegalArgumentException", e)
    }
    return uri
}

private fun openCamera(fragment: Fragment) {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).let { intent ->
        fragment.context?.let { context ->
            if (context.packageManager != null) {
                val uri = getCameraOutputUri(context)
                if (uri != null) {
                    val resInfoList = context.packageManager.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )
                    resInfoList.forEach { resolveInfo ->
                        val name = resolveInfo.activityInfo.packageName
                        fragment.context?.grantUriPermission(
                            name,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    try {
                        if (fragment.isAdded) {
                            fragment.startActivityForResult(intent, REQUEST_CODE_SELECT_CAMERA)
                        }
                    } catch (e: SecurityException) {
                        AppLogger.e(TAG, "Failed to open camera", e)
                    }
                }
            }
        }
    }
}

private fun checkListOfPermission(
    activity: Activity,
    permissions: Collection<String>,
    item: CHOOSER,
    fragment: Fragment
) {
    Dexter.withContext(activity)
        .withPermissions(permissions)
        .withListener(object : MultiplePermissionsListener {

            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                p0?.let {
                    if (it.areAllPermissionsGranted()) {
                        if (item == CHOOSER.CAMERA) {
                            openCamera(fragment)
                        } else if (item == CHOOSER.GALLERY) {
                            openAlbum(fragment)
                        }
                    } else if (it.isAnyPermissionPermanentlyDenied) {
                        PermissionUtilsNew.showPermissionSettingsDialog(activity)
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                permissionToken: PermissionToken?
            ) {
                permissionToken?.continuePermissionRequest()
            }
        })
        .check()
}




