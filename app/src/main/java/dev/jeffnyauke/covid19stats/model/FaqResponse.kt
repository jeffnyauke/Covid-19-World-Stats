package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class FaqResponse(
    @Json(name = "source")
    var source: String? = null,
    @Json(name = "data")
    var data: List<FaqsData>? = null
)