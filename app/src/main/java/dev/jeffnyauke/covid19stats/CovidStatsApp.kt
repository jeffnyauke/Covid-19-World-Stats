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

package dev.jeffnyauke.covid19stats

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import dev.jeffnyauke.covid19stats.di.networkModule
import dev.jeffnyauke.covid19stats.di.viewModelModule
import dev.jeffnyauke.covid19stats.ui.analytics.CrashReport
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker
import dev.jeffnyauke.covid19stats.ui.analytics.di.analyticsModule
import dev.jeffnyauke.covid19stats.ui.settings.di.settingsModule
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.get
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set
import dev.jeffnyauke.covid19stats.utils.di.utilsModule
import dev.jeffnyauke.covid19stats.worker.NotificationWorkerManager
import dev.jeffnyauke.covid19stats.worker.di.syncModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CovidStatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                networkModule,
                viewModelModule,
                analyticsModule,
                settingsModule,
                utilsModule,
                syncModule
            )
        }

        val tracker: Tracker by inject()
        tracker.initialize()

        val crashReport: CrashReport by inject()
        crashReport.initialize()

        val prefs: SharedPreferences by inject()
        val notificationWorkerManager: NotificationWorkerManager by inject()

        val keyThemePicker = getString(R.string.pref_key_theme_picker)
        val keyShowNotifications = getString(R.string.pref_key_notifications)
        val keyInterval = getString(R.string.pref_key_intervals)

        if (!prefs.contains(keyShowNotifications)) {
            prefs[keyShowNotifications] = true
        }

        val showNotifications = prefs[keyShowNotifications, true]!!
        if (showNotifications) {
            if (!prefs.contains(keyInterval)) {
                prefs[keyInterval] = getString(R.string.interval_value_half_hour)
            }

            val interval = prefs[keyInterval, getString(R.string.interval_value_half_hour)]
            notificationWorkerManager.initialize(interval!!.toLong())
        }

        if (!prefs.contains(keyThemePicker)) {
            prefs[keyThemePicker] = getString(R.string.theme_value_light)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            val theme = prefs[keyThemePicker, getString(R.string.theme_value_light)]
            val newMode = when (theme) {
                getString(R.string.theme_value_dark) -> AppCompatDelegate.MODE_NIGHT_YES
                getString(R.string.theme_value_light) -> AppCompatDelegate.MODE_NIGHT_NO
                getString(R.string.theme_value_default) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> throw IllegalArgumentException("Mode with value: $theme is not supported")
            }
            AppCompatDelegate.setDefaultNightMode(newMode)
        }
    }
}