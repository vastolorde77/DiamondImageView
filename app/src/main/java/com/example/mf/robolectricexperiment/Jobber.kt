package com.example.mf.robolectricexperiment

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import android.util.Log

class Jobber : JobService(){

    lateinit var notificationManager: NotificationManager

    fun createChannel(){
        notificationManager = NotificationHelper(this).manager
        if (Build.VERSION.SDK_INT >= 28){
            val notificationChannel = notiChan {
                id = "ID"
                name = "Job Service"
                importance = NotificationManager.IMPORTANCE_HIGH
            }.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "From Job Service"
            }

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("NIGGA IT STOPPED","WHAT")
        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        val pending = PendingIntent.getActivity(this,0, Intent(this,MainActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
//        val builder =buildNotification {
//            context = this.context
//            channel = "ID"
//        }.apply {
//            setContentTitle("Job Service")
//            setContentText("Job running")
//            setContentIntent(pending)
//            setSmallIcon(R.drawable.ic_job_running)
//            setPriority(NotificationCompat.PRIORITY_HIGH)
//            setDefaults(NotificationCompat.DEFAULT_ALL)
//            setAutoCancel(true)
//        }
        Log.d("IS_IT_RUNNING","YEAH")
        return true
    }

}


