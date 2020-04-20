package dev.jeffnyauke.covid19stats.ui.analytics

interface Tracker {
    fun initialize()
    fun track(event: Event)
    fun enableTracking(enabled: Boolean)
}
