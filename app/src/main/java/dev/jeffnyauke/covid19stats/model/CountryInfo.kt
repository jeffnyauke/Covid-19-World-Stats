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
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo(
    @Json(name = "_id")
    var id: Int? = null,
    @Json(name = "iso2")
    var iso2: String? = null,
    @Json(name = "iso3")
    var iso3: String? = null,
    @Json(name = "lat")
    var lat: Double? = null,
    @Json(name = "long")
    var long: Double? = null,
    @Json(name = "flag")
    var flag: String? = null

) : Parcelable