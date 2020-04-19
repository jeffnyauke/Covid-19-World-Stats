package dev.jeffnyauke.covid19stats.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.ActivityMainBinding
import dev.jeffnyauke.covid19stats.worker.NotificationWorker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_global, R.id.navigation_country, R.id.navigation_news
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        initWorker()
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(30, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            JOB_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    companion object {
        const val JOB_TAG = "notificationWorkTag"
    }
}
