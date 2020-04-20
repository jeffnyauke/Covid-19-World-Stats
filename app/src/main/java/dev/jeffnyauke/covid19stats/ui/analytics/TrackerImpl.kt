package dev.jeffnyauke.covid19stats.ui.analytics

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
    }
}
