package dev.jeffnyauke.covid19stats.ui.analytics

abstract class Event {
    abstract val name: String
    abstract val data: Map<String, String>
}
