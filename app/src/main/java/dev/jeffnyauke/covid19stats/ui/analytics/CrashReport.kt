package dev.jeffnyauke.covid19stats.ui.analytics

interface CrashReport {
    fun initialize()
    fun enableCrashReporting(enabled: Boolean)
}
