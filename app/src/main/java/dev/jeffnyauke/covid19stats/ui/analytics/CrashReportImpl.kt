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

package dev.jeffnyauke.covid19stats.ui.analytics

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set

internal class CrashReportImpl(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : CrashReport {
    override fun initialize() {
        val keyCrashReport = context.getString(R.string.pref_key_crash_reports)
        if (!sharedPreferences.contains(keyCrashReport)) {
            sharedPreferences[keyCrashReport] = true
            enableCrashReporting(true)
        }
    }

    override fun enableCrashReporting(enabled: Boolean) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }
}
