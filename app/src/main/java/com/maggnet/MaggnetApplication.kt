package com.maggnet

import android.app.Application
import android.content.Context
import android.os.Parcel
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.maggnet.utils.AppLanguageUtils
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class MaggnetApplication : MultiDexApplication() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    var lastOpenedTab: Int = -1


    companion object {

        lateinit var instance: MaggnetApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        preferenceManager.getLanguagePreference().let { AppLanguageUtils.initAppLocale(this, it) }

        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                token?.let {
                    Log.e("token ---->>", it)
                    preferenceManager.setFCMToken(it)
                }
            })
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}