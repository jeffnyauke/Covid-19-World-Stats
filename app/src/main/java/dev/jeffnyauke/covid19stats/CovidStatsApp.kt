package dev.jeffnyauke.covid19stats

import android.app.Application
import dev.jeffnyauke.covid19stats.di.networkModule
import dev.jeffnyauke.covid19stats.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CovidStatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, viewModelModule)
        }
    }
}