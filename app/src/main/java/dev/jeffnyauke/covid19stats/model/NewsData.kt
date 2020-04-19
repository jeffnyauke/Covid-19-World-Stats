package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class NewsData(
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "link")
    var link: String? = null,
    @Json(name = "date")
    var date: String? = null

)