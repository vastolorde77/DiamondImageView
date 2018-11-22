package com.example.mf.robolectricexperiment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

fun notiChan(func: notiChanBuilder.() -> Unit) : NotificationChannel = notiChanBuilder().apply(func).build()

class notiChanBuilder{
    var id : String = "ID"
    var name : String = "Channel"
    var importance : Int = NotificationManager.IMPORTANCE_HIGH
    fun build() : NotificationChannel = NotificationChannel(id,name,importance)
}

fun buildNotification(func: notiCompBuilder.() -> Unit) : NotificationCompat.Builder = notiCompBuilder().apply(func).build()

class notiCompBuilder{
    lateinit var context : Context
    var channel : String = "1"
    fun build() : NotificationCompat.Builder = NotificationCompat.Builder(context,channel)
}