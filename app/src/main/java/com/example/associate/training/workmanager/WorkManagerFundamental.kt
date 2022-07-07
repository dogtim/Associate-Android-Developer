package com.example.associate.training.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.associate.training.CHANNEL_ID
import com.example.associate.training.Entry
import com.example.associate.training.MainEntryAdapter
import com.example.associate.training.busschedule.BusScheduleActivity
import com.example.associate.training.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import com.example.associate.training.R
import com.example.associate.training.databinding.ActivityWorkManagerBinding

open class WorkManagerFundamental : AppCompatActivity() {
    private lateinit var binding: ActivityWorkManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // You must create the notification channel before posting any notifications on Android 8.0 and higher,
        // you should execute this code as soon as your app starts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.appwidget_text)
            val descriptionText = getString(R.string.looper_event)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.oneTimeButton.setOnClickListener {
            val oneTimeWorker = OneTimeWorkMediator()
            oneTimeWorker.trigger(this, 3000)
        }

        binding.periodicButton.setOnClickListener {
            val periodWorker = PeriodicWorkMediator()
            periodWorker.trigger(this)
        }

        binding.fakePeriodicButton.setOnClickListener {
            val fakePeriodWorker = FakePeriodicWorkMediator()
            fakePeriodWorker.trigger(this)
        }
    }
}

