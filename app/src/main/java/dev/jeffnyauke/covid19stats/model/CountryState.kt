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

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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