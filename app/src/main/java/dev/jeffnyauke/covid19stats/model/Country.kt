/*
 *
 *  Copyright (c) 2020 jeffnyauke@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package dev.jeffnyauke.covid19stats.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "updated")
    var updated: Long = 0L,
    @Json(name = "country")
    var country: String? = null,
    @Json(name = "countryInfo")
    var countryInfo: CountryInfo? = null,
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
    var casesPerOneMillion: Double? = 0.toDouble(),
    @Json(name = "deathsPerOneMillion")
    var deathsPerOneMillion: Double? = 0.toDouble(),
    @Json(name = "tests")
    var tests: Int = 0,
    @Json(name = "testsPerOneMillion")
    var testsPerOneMillion: Double? = 0.toDouble()

) : Parcelable