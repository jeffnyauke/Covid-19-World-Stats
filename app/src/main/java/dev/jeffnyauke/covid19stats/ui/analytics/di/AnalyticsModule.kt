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
