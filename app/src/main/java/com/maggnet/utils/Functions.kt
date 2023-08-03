package com.maggnet.utils

import android.app.Activity
import android.graphics.Color
import android.text.TextUtils
import android.util.Patterns
import android.view.View

object Functions {

    @JvmStatic
    fun changeStatusBar(activity: Activity, color: Int) {
        val view = activity.window.decorView
        var flags = view.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        view.systemUiVisibility = flags
        activity.window.statusBarColor = color
    }

    @JvmStatic
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}