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
