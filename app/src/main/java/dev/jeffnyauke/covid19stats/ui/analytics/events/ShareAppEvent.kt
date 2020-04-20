package dev.jeffnyauke.covid19stats.ui.analytics.events

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE
import com.google.firebase.analytics.FirebaseAnalytics.Param.METHOD
import dev.jeffnyauke.covid19stats.ui.analytics.Event

class ShareAppEvent : Event() {
    override val name: String = FirebaseAnalytics.Event.SHARE
    override val data = mapOf(
        CONTENT_TYPE to "App",
        METHOD to "About Screen"
    )

}
