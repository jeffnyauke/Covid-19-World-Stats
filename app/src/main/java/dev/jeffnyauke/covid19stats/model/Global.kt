package dev.jeffnyauke.covid19stats.model

import com.squareup.moshi.Json

data class Global(
    @Json(name = "updated")
    var updated: Long = 0L,
    @Json(name = "cases")
    var cases: Int = 0,
    @Json(name = "todayCases")
    var todayCases: Int = 0,
    @Json(name = "deaths")
    var deaths: Int = 0,
    @Json(name = "todayDeaths")
    var todayDeaths: Int = 0,
    @Json(name = "recovered")
    var recovered: Int = 0,
    @Json(name = "active")
    var active: Int = 0,
    @Json(name = "critical")
    var critical: Int = 0,
    @Json(name = "casesPerOneMillion")
    var casesPerOneMillion: Int = 0,
    @Json(name = "deathsPerOneMillion")
    var deathsPerOneMillion: Int = 0,
    @Json(name = "tests")
    var tests: Int = 0,
    @Json(name = "testsPerOneMillion")
    var testsPerOneMillion: Double? = 0.toDouble(),
    @Json(name = "affectedCountries")
    var affectedCountries: Int = 0

)