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
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker
import dev.jeffnyauke.covid19stats.ui.analytics.events.SettingsClickEvent
import dev.jeffnyauke.covid19stats.utils.addOnPropertyChanged
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModel()
    private val firebaseAnalytics: FirebaseAnalytics by inject()
    private val tracker: Tracker by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_settings)
        }

        viewModel.mode.addOnPropertyChanged {
            setDefaultNightMode(it.get())
        }

        val intervals = findPreference<ListPreference>(getString(R.string.pref_key_intervals))!!
        intervals.setOnPreferenceChangeListener { _, newValue ->
            viewModel.changeTheme(newValue as String)
            true
        }

        val theme = findPreference<ListPreference>(getString(R.string.pref_key_theme_picker))!!
        theme.setOnPreferenceChangeListener { _, newValue ->
            viewModel.changeNotificationInterval(newValue as String)
            true
        }

        findPreference<SwitchPreference>(getString(R.string.pref_key_analytics))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableTracking(newValue as Boolean)
                true
            }
        }

        findPreference<SwitchPreference>(getString(R.string.pref_key_notifications))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableNotifications(newValue as Boolean)
                true
            }
        }

        findPreference<Preference>(getString(R.string.pref_key_version))!!.apply {
            summary = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            tracker.track(SettingsClickEvent("Version"))
        }

        findPreference<SwitchPreference>(getString(R.string.pref_key_crash_reports))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableCrashReport(newValue as Boolean)
                true
            }
        }

        findPreference<Preference>(getString(R.string.pref_key_send_feedback))!!.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                sendFeedback(requireActivity())
                tracker.track(SettingsClickEvent("Send feedback"))
                true
            }
        }

        firebaseAnalytics.setCurrentScreen(requireActivity(), "Settings Fragment", null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavHostFragment.findNavController(this)
                .navigateUp()
        }
        return true
    }


    /**
     * Email client intent to send support mail
     * Appends the necessary device information to email body
     * useful when providing support
     */
    private fun sendFeedback(context: Context) {
        var body: String? = null
        try {
            body = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            body =
                "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                        Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                        "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER
        } catch (e: PackageManager.NameNotFoundException) {
        }

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("jeffnyauke@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Query from android app")
            putExtra(Intent.EXTRA_TEXT, body)
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}
