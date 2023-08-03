package com.maggnet.utils

import android.app.Activity
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.maggnet.R

class WebViewClientUtil(
    val overRideUrl: Boolean,
    val webPageErrorCallBack: (errorTextId: Int) -> Unit
) :
    WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return overRideUrl
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        when (error?.errorCode) {
            ERROR_HOST_LOOKUP -> {
                webPageErrorCallBack(R.string.error_internet_not_available)
            }
        }
        super.onReceivedError(view, request, error)
    }
}