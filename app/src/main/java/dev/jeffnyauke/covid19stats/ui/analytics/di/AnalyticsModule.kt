package dev.jeffnyauke.covid19stats.ui.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.ui.analytics.CrashReport
import dev.jeffnyauke.covid19stats.ui.analytics.CrashReportImpl
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker
import dev.jeffnyauke.covid19stats.ui.analytics.TrackerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(androidContext()) }
    single<Tracker> { TrackerImpl(get(), androidContext(), get()) }
    single<CrashReport> { CrashReportImpl(androidContext(), get()) }
}
