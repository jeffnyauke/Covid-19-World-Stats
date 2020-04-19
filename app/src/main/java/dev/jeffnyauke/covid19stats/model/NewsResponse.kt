package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "data")
    var data: List<NewsData>? = null

)