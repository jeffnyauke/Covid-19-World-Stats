package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class CountryState(
    @Json(name = "state")
    var state: String? = null,
    @Json(name = "cases")
    var cases: Int = 0,
    @Json(name = "todayCases")
    var todayCases: Int = 0,
    @Json(name = "deaths")
    var deaths: Int = 0,
    @Json(name = "todayDeaths")
    var todayDeaths: Int = 0,
    @Json(name = "active")
    var active: Int = 0,
    @Json(name = "tests")
    var tests: Int = 0,
    @Json(name = "testsPerOneMillion")
    var testsPerOneMillion: Int = 0

)