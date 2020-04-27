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
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set

internal class TrackerImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : Tracker {
    override fun initialize() {
        val keyAnalytics = context.getString(R.string.pref_key_analytics)
        if (!sharedPreferences.contains(keyAnalytics)) {
            sharedPreferences[keyAnalytics] = true
            enableTracking(true)
        }
    }

    override fun track(event: Event) {

        val dataBundle: Bundle? =
            if (event.data.isEmpty())
                null
            else
                Bundle().apply {
                    event.data.forEach { (key, value) ->
                        putString(key, value)
                    }
                }
        firebaseAnalytics.logEvent(event.name, dataBundle)
    }

    override fun enableTracking(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
        Log.e("TIME INFO", "trak")
    }
}
