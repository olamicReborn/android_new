package com.maggnet.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

fun Context.showShortToast(textRes: Int) {
    showShortToast(getString(textRes))
}

fun Context.showShortToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(textRes: Int) {
    showLongToast(getString(textRes))
}

fun Context.showLongToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
