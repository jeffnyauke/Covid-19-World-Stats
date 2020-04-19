package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class ProtectiveMeasures(
    @Json(name = "source")
    var source: String? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "content")
    var content: List<String>? = null
)