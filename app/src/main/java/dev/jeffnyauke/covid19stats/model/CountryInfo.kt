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