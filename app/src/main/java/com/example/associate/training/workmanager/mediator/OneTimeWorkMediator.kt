package com.example.associate.training.workmanager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.associate.training.CHANNEL_ID
import com.example.associate.training.R
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * One Time Worker Demo Codes
 *
 *
 */
class OneTimeWorkMediator {

    fun trigger(context: Context, initialDelay: Long) {

        val work =
            OneTimeWorkRequestBuilder<OneTimeScheduleWorker>()
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .addTag("WORK_TAG")
                .build()

        WorkManager.getInstance(context).enqueue(work)
    }

}

class OneTimeScheduleWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.android_cupcake)
            .setContentTitle("Scheduled notification")
            .setContentText("Hello from one-time worker!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }

        return Result.success()
    }

}