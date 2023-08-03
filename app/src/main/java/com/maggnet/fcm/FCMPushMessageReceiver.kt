package com.maggnet.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.ui.welcome.activity.AmountEntryActivity
import com.maggnet.utils.AppLogger
import com.maggnet.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
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
            return
        }
//        if (data.isNotEmpty()) {
//            showNotification(this, data["title"], "")
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