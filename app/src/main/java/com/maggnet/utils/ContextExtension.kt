package com.maggnet.utils

import android.content.Context
import android.view.View

fun Context.checkRTLDirection(): Boolean {
    return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
}