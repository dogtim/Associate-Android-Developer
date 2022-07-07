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

open class WorkManagerFundamental : AppCompatActivity() {
    private lateinit var adapter: MainEntryAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()

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
        // scheduleOneTimeNotification(5000)
        // schedulePeriodicNotifications()
    }

    private fun setupAdapter() {
        adapter = MainEntryAdapter(getList())
        binding.tutorsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.tutorsRecyclerView.adapter = adapter
    }

    open fun getList(): List<Entry> {
        return listOf(
            Entry("WorkManager", WorkManagerActivity::class.java),
            Entry("ROOM Database \n sql", BusScheduleActivity::class.java)
        )
    }

    fun scheduleOneTimeNotification(initialDelay: Long) {
        val work =
            OneTimeWorkRequestBuilder<OneTimeScheduleWorker>()
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .addTag("WORK_TAG")
                .build()

        WorkManager.getInstance(this).enqueue(work)
    }

    fun schedulePeriodicNotifications() {

        val periodicWork = PeriodicWorkRequestBuilder<OneTimeScheduleWorker>(
            15, TimeUnit.MINUTES
        ).addTag("WORK_TAG").build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "WORK_NAME",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWork
            )
    }
}

class OneTimeScheduleWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.i("dogtim", "doWork")
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.android_cupcake)
            .setContentTitle("Scheduled notification")
            .setContentText("Hello from one-time worker!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }

        return Result.retry()
    }

}
