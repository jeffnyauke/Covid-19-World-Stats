package dev.jeffnyauke.covid19stats.ui.settings.di

import dev.jeffnyauke.covid19stats.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(androidContext(), get(), get()) }
}