package com.iiitd.ucsf.activities

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iiitd.ucsf.R
import kotlinx.android.synthetic.main.single_row_audios.view.*

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage ) {
        Log.d("TAG", "From: ${remoteMessage?.from}")
        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d("TAG", "Message Notification Body: ${it.body}")
            //Message Services handle notification
            val notification = NotificationCompat .Builder(this)
                .setContentTitle(remoteMessage.from)
                .setContentText(it.body)
               .setSmallIcon(R.drawable.audio)
                .build()
            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(/*notification id*/0, notification)

        }
    }

    override fun onNewToken(token: String) {
        //handle token
    }
}