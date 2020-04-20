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
            modules(networkModule, viewModelModule, analyticsModule, settingsModule, utilsModule)
        }

        val tracker: Tracker by inject()
        tracker.initialize()

        val crashReport: CrashReport by inject()
        crashReport.initialize()

        val sharedPreferences: SharedPreferences by inject()
        val keyThemePicker = getString(R.string.pref_key_theme_picker)

        if (!sharedPreferences.contains(keyThemePicker)) {
            sharedPreferences[keyThemePicker] = getString(R.string.theme_value_default)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            val theme = sharedPreferences[keyThemePicker, getString(R.string.theme_value_default)]
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