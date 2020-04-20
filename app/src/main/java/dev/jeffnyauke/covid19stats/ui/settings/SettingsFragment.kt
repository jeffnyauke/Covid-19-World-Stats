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

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.utils.addOnPropertyChanged
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.settings_name)
        }

        viewModel.mode.addOnPropertyChanged {
            setDefaultNightMode(it.get())
        }

        val theme = findPreference<ListPreference>(getString(R.string.pref_key_theme_picker))!!
        theme.setOnPreferenceChangeListener { _, newValue ->
            viewModel.changeTheme(newValue as String)
            true
        }

        findPreference<SwitchPreference>(getString(R.string.pref_key_analytics))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableTracking(newValue as Boolean)
                true
            }
        }

        findPreference<SwitchPreference>(getString(R.string.pref_key_crash_reports))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableCrashReport(newValue as Boolean)
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavHostFragment.findNavController(this)
                .navigateUp()
        }
        return true
    }
}
