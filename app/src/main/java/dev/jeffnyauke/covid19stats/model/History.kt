package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class History(
    @Json(name = "country")
    var country: String? = null,
    @Json(name = "province")
    var province: List<String>? = null,
    @Json(name = "timeline")
    var timeline: Timeline? = null

)