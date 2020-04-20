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
