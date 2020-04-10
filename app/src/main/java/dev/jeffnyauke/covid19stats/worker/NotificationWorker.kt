package dev.jeffnyauke.covid19stats.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.model.Global
import dev.jeffnyauke.covid19stats.repository.CovidStatsRepository
import dev.jeffnyauke.covid19stats.ui.main.MainActivity
import dev.jeffnyauke.covid19stats.utils.State
import dev.jeffnyauke.covid19stats.utils.getFormattedNumber
import dev.jeffnyauke.covid19stats.utils.getPeriod
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import org.koin.core.KoinComponent
import org.koin.core.get
import java.util.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    @SuppressLint("StringFormatInvalid")
    private fun showNotification(totalCount: String, time: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = context.getString(R.string.default_notification_channel_id)
        val channelName = context.getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, R.color.dark_red))
            .setSmallIcon(R.drawable.ic_stat_notification_icon)
            .setContentTitle(context.getString(R.string.text_confirmed_cases, totalCount))
            .setContentText(context.getString(R.string.text_last_updated, time))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    override suspend fun doWork(): Result = coroutineScope {
        Log.d(javaClass.simpleName, "Worker Started!")

        val repository: CovidStatsRepository = get()

        val result = withContext(Dispatchers.Default) {
            repository.getGlobalData().toList()
        }.filterIsInstance<State.Success<Global>>()

        if (result.isNullOrEmpty()) {
            Log.d(javaClass.simpleName, "Work Failed. Retrying...")
            Result.retry()
        } else {
            val totalDetails = result[0].data

            showNotification(
                getFormattedNumber(totalDetails.cases),
                getPeriod(
                    Date(totalDetails.updated)
                )
            )
            Log.d(javaClass.simpleName, "Notification Displayed!")
            Log.d(javaClass.simpleName, "Work Succeed...")

            Result.success()
        }
    }
}
