package com.maggnet.ui.home.fragment.qrcode.scan

import android.net.Uri
import com.maggnet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
) : BaseViewModel<QRCodeScanNavigator>() {

    var selectedImageFromGallery: Uri? = null


}