package com.example.associate.training.workmanager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.associate.training.CHANNEL_ID
import com.example.associate.training.R
import com.example.associate.training.TAG_OUTPUT
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Use OneTimeWorkRequestBuilder to achieve the periodic function
 *
 * > At the moment, if you need to execute a worker at *roughly the same time*,
 * > every day, your best option is to use a OneTimeWorkRequest with an
 * > initial delay so that you execute it at the right time:
 *
 * Please read this [article](https://medium.com/androiddevelopers/workmanager-periodicity-ff35185ff006) first
 *
 */
class FakePeriodicWorkMediator {

    fun trigger(context: Context) {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 16)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest =
            OneTimeWorkRequestBuilder<DailyWorker>()
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag("WORK_TAG")
                .build()
        WorkManager.getInstance(context).enqueue(dailyWorkRequest)
    }
}

class DailyWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 5)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<DailyWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag(TAG_OUTPUT)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
        return Result.success()
    }

}