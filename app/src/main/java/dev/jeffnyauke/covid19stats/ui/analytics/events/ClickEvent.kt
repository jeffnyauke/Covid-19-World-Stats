package dev.jeffnyauke.covid19stats.ui.analytics.events

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_NAME
import com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_VARIANT
import dev.jeffnyauke.covid19stats.ui.analytics.Event

class ClickEvent(titleName: String, clickType: ClickVariant) : Event() {

    override val name: String = FirebaseAnalytics.Event.SELECT_ITEM

    override val data = mapOf(
        ITEM_NAME to titleName,
        ITEM_VARIANT to clickType.variantName
    )

    enum class ClickVariant(val variantName: String) {
        NEWS("News"),
        COUNRTY("Country")
    }
}
