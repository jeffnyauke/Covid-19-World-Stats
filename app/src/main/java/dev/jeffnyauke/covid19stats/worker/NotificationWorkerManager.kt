/*
 *
 *  Copyright (c) 2020 jeffnyauke@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package dev.jeffnyauke.covid19stats.worker

import androidx.work.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class NotificationWorkerManager(private val workManager: WorkManager) {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private lateinit var notificationWorkRequest: PeriodicWorkRequest

    fun initialize(interval: Long) {
        notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(interval, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        start()
    }

    fun start() {
        workManager.enqueueUniquePeriodicWork(
            JOB_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    companion object {
        const val JOB_TAG = "notificationWorkTag"
    }
}