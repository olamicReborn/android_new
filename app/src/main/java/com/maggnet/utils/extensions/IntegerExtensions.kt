package com.maggnet.utils.extensions

import android.content.res.Resources

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.Companion.empty: Int get() = 0