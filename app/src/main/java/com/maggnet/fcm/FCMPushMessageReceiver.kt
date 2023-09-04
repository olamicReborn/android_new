package com.maggnet.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.maggnet.R
import com.maggnet.ui.welcome.activity.AmountEntryActivity
import com.maggnet.ui.welcome.activity.CouponActivity
import com.maggnet.utils.AppLogger
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class FCMPushMessageReceiver : FirebaseMessagingService() {

    val TAG = FCMPushMessageReceiver::class.java.simpleName

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onNewToken(token: String) {
        if (preferenceManager.getFCMToken() != token && token.isNotEmpty()) {
            preferenceManager.setFCMToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val data: Map<String, String> = remoteMessage.data
        AppLogger.d(TAG, "FCM DATA => $data")
        if (data == null || data.isEmpty()) {
            MainScope().launch {
                Toast.makeText(this@FCMPushMessageReceiver, "No push received", Toast.LENGTH_LONG).show()
            }
            return

        } else {
            MainScope().launch {
                Toast.makeText(this@FCMPushMessageReceiver, data.toString(), Toast.LENGTH_LONG).show()
            }
            val jsonObject = JSONObject(data)
            preferenceManager.setUserId(jsonObject.getString("user_id"))
            preferenceManager.setUserCountryCode(jsonObject.getString("country_code"))
            preferenceManager.setMobileNumberForRegistration(jsonObject.getString("phone_number"))
            preferenceManager.setOtpRequested(false)

            val openActivityIntent = Intent(
                this,
                CouponActivity::class.java
            )
            this.startActivity(openActivityIntent)

        }
//        if (data.isNotEmpty()) {
//            showNotification(this, data["title"], "")  {user_id=2166}
//        }
    }

    private fun showNotification(context: Context, title: String?, message: String?) {
        // The id of the channel.
        val CHANNEL_ID = "MaggnetNotification"
        val CHANNEL_NAME = "Banner Notification"
        val notificationIntent = Intent(context, AmountEntryActivity::class.java)
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        @SuppressLint("UnspecifiedImmutableFlag") val pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, 0
        )
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val defaultChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(defaultChannel)
        }
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setStyle(NotificationCompat.BigTextStyle().bigText(title))
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(Notification.PRIORITY_MAX)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent) as NotificationCompat.Builder
        val notification = builder.build()
        notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
        notificationManager.notify(100, notification)
    }
}