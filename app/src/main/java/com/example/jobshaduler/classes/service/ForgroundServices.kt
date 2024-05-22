package com.example.jobshaduler.classes.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.singleton.emailandpass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class ForgroundServices : Service() {

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val NOTIFICATION_CHANNEL_ID = "MyNotificationChannel"
        private const val NOTIFICATION_ID = 123
    }

    override fun onCreate() {
        super.onCreate()
        videoCallServices(emailandpass.empid!!)
        createNotificationChannel()
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        FirebaseDatabase.getInstance().getReference("employ/${emailandpass.empid}/notificatiton")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.value
                        val bo = data.toString()
                        if (bo.toBoolean()) {
                            showNotificationWithVibration(applicationContext, "task", "you have a new task")
                            val vibrationEffect1: VibrationEffect
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
                                vibrator.cancel()
                                vibrator.vibrate(vibrationEffect1)
                            }
                        }
                    } else {
                        Log.e("not get ", "ni mela ")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("loaderror", "Failed to read data: ${error.message}")
                }
            })

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("jobshaduler")
            .setContentText("jobshaduler service is running")
            .setSmallIcon(R.drawable.job)
            .build()


        startForeground(1, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showNotificationWithVibration(context: Context, title: String, message: String) {
        createNotificationChannel(context)
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.job)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "My Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
    private fun videoCallServices(userID: String) {
        val appID: Long = 1232378385 // your App ID of Zoge Cloud Project
        val appSign = "ca2395df49aa00f574fa36961dd9c09b73585e0014cd6a50700a5d8570283a9d" // your App Sign of Zoge Cloud Project
        val application = application // Android's application context
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        //    callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true
        val notificationConfig = ZegoNotificationConfig()
        notificationConfig.sound = "zego_uikit_sound_call"
        notificationConfig.channelID = "CallInvitation"
        notificationConfig.channelName = "CallInvitation"
        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            userID,
            userID,
            callInvitationConfig
        )
    }
}
