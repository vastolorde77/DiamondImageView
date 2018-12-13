package com.example.mf.robolectricexperiment

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

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
        Glide.with(this).load("https://picsum.photos/${Random.nextInt(200,500)}").into(imageView)

    }

    override fun onResume() {
        super.onResume()

    }

    private fun setOnClicks() {
        schedule.setOnClickListener {

        }
        cancel.setOnClickListener {
//            imageView.toggleRound()
        }

    }


}
