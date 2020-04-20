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

package dev.jeffnyauke.covid19stats.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.ui.analytics.CrashReport
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker

class SettingsViewModel(
    private val context: Context,
    private val crashReport: CrashReport,
    private val tracker: Tracker
) : ViewModel() {

    val mode = ObservableInt(Int.MIN_VALUE)

    fun enableCrashReport(enabled: Boolean) {
        crashReport.enableCrashReporting(enabled)
    }

    fun enableTracking(enabled: Boolean) {
        tracker.enableTracking(enabled)
    }

    fun changeTheme(theme: String) {
        val newMode = when (theme) {
            context.getString(R.string.theme_value_dark) -> MODE_NIGHT_YES
            context.getString(R.string.theme_value_light) -> MODE_NIGHT_NO
            context.getString(R.string.theme_value_default) -> MODE_NIGHT_FOLLOW_SYSTEM
            else -> throw IllegalArgumentException("Mode with value: $theme is not supported")
        }
        mode.set(newMode)
    }
}
