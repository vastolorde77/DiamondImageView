package com.example.mf.robolectricexperiment

import android.app.Notification
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.jvm.jvmName

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName
    private val notificationHelper by lazy{NotificationHelper(this)}
    private val NOTI = mapOf<String,Int>("PRI1" to 1100,"PRI2" to 1101,"SECOND1" to 1200, "SECOND2" to 1201)

    companion object {
        val JOB_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnClicks()

    }

    private fun setOnClicks() {
        schedule.setOnClickListener {

        }
        cancel.setOnClickListener {
        }

    }


}
