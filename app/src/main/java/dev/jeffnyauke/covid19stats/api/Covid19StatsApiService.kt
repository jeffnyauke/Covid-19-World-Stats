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

package dev.jeffnyauke.covid19stats.api

import dev.jeffnyauke.covid19stats.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Covid19StatsApiService {

    @GET("/v2/all")
    suspend fun getGlobalData(): Response<Global>

    @GET("/v2/countries")
    suspend fun getAllCountriesData(@Query("sort") sort: String): Response<List<Country>>

    @GET("/v2/countries/{country}")
    suspend fun getAllCountriesData(@Path("country") country: String, @Query("strict") strict: Boolean): Response<Country>

    @GET("/v2/states")
    suspend fun getAllStatesData(): Response<List<CountryState>>

    @GET("/v2/historical/all")
    suspend fun getAllHistoricalData(): Response<Timeline>

    @GET("/v2/historical/{country}")
    suspend fun getCountryHistoricalData(@Path("country") country: String): Response<History>

    @GET
    suspend fun getNews(@Url url: String): Response<NewsResponse>

    @GET
    suspend fun getFaqs(@Url url: String): Response<FaqResponse>

    @GET
    suspend fun getProtectiveMeasures(@Url url: String): Response<ProtectiveMeasures>

    companion object {
        const val BASE_URL = "https://corona.lmao.ninja/"
        const val FAQS_URL = "http://covid19-news.herokuapp.com/api/covid19/faqs"
        const val NEWS_URL = "http://covid19-news.herokuapp.com/api/covid19/news"
        const val PROTECTIVE_MEASURES_URL =
            "http://covid19-news.herokuapp.com/api/covid19/protective-measures"
    }
}
