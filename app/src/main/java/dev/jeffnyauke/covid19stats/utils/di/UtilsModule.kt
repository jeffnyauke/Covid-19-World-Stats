package dev.jeffnyauke.covid19stats.utils.di

import dev.jeffnyauke.covid19stats.utils.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    single { PreferenceHelper.defaultPrefs(androidContext()) }
}
