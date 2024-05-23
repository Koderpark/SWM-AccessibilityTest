package com.example.parsingtest;

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            displayNotification()
        }

        if(intent.hasExtra("fromNotification")){
            print("HELLO WORLD")
            Log.i("Custom", "Notification Click Checked--------------------")
            moveTaskToBack(true)
        }
    }

    private fun displayNotification() {
        startActivity(
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );


        val notificationId = 45

        val activityIntent = Intent(this, MainActivity::class.java)
        activityIntent.putExtra("fromNotification",0)
        val pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)


        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_example)
            .setContentTitle("Example")
            .setContentText("This is Notification Test")
            .setContentIntent(pendingIntent)
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
