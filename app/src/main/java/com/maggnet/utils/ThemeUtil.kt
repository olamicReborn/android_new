package com.maggnet.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.maggnet.MaggnetApplication

object ThemeUtil {


    fun isDarkModeEnabled(context: Context?): Boolean {
        var nightModeFlags = 0x10
        if (context != null) nightModeFlags =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> false
        }
    }

    fun Activity.makeStatusBarTransparent() {
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (isAppsDarkModeEnabled(context)) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            } else {
                decorView.systemUiVisibility =
                     View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            statusBarColor = Color.WHITE
            navigationBarColor = Color.TRANSPARENT
        }
    }

    /**
     * checking whether app is in dark mode or not.
     *
     * 1. In first launch, system is dark mode then app should be show in dark mode
     * 2. If app is set values 2 in shared pref of "theme_mode_state" then app is in dark mode
     */
    fun isAppsDarkModeEnabled(context: Context?): Boolean {
        // Assuming that none of the OEM has not implemented dark mode on below P.

        val preferenceManager = context?.let {
            PreferenceManager(context)
        }

//        if (preferenceManager?.getNightModePreference() == 0 && !isDarkModeEnabled(context))
//            return false
//        if (preferenceManager?.getNightModePreference() == -1 && isDarkModeEnabled(context))
//            return true


        return false

//        return when (preferenceManager?.getNightModePreference()) {
//            0,
//            AppCompatDelegate.MODE_NIGHT_YES,
//            -> true
//
//            else -> false
//        }
    }


    fun setStatusBarColor(activity : Activity, color : Int){
        activity.window.statusBarColor = ContextCompat.getColor(MaggnetApplication.instance, color)
    }

    fun setColor(
        activity: Activity,
        @ColorInt color: Int,
        @IntRange(from = 0, to = 255) statusBarAlpha: Int
    ) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.statusBarColor = color
    }


}