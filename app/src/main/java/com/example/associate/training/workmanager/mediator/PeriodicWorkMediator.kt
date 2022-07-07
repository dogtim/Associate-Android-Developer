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
 * Periodic Worker Demo Codes
 *
 * Please read this [article](https://medium.com/androiddevelopers/workmanager-periodicity-ff35185ff006) first
 *
 */
class PeriodicWorkMediator {

    fun trigger(context: Context) {

        val periodicWork = PeriodicWorkRequestBuilder<PeriodicScheduleWorker>(
            15, TimeUnit.MINUTES
        ).addTag("WORK_TAG").build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "WORK_NAME",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWork
            )
    }
}

class PeriodicScheduleWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.android_cupcake)
            .setContentTitle("Scheduled notification")
            .setContentText("Hello from periodic worker!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }
        // Periodic work never ends up in a SUCCEEDED status; it keeps running until it is cancelled.
        // When you call Result#success() or Result#failure() from a periodic worker,
        // it transitions back to the ENQUEUED status waiting for the next execution.
        return Result.retry()
    }

}