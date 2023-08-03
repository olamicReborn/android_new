package com.maggnet.ui.extensions

import java.lang.Exception
import java.util.*

val String.Companion.empty: String get() = ""

fun String?.safeGet(): String = this ?: String.empty

fun String.getUrlFromString(): String {
    val p = android.util.Patterns.WEB_URL
    val m = p.matcher(this)
    try {
        if (m.find()) {
            val furl = m.group()
            return furl.toLowerCase(Locale.ROOT)
        }
    } catch (ignored: Exception) {
    }
    return this
}
