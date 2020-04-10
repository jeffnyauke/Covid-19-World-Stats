package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class Timeline(
    @Json(name = "cases")
    var cases: Map<String, Int>? = null,
    @Json(name = "deaths")
    var deaths: Map<String, Int>? = null,
    @Json(name = "recovered")
    var recovered: Map<String, Int>? = null

)