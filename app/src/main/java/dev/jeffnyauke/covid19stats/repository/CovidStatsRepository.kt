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

package dev.jeffnyauke.covid19stats.repository

import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import dev.jeffnyauke.covid19stats.api.Covid19StatsApiService
import dev.jeffnyauke.covid19stats.model.*
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class CovidStatsRepository(
    private val apiService: Covid19StatsApiService,
    private val parser: Parser
) {

    fun getGlobalData(): Flow<State<Global>> {
        return object : NetworkBoundRepository<Global>() {
            override suspend fun fetchFromRemote(): Response<Global> = apiService.getGlobalData()
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllCountriesData(sort: String): Flow<State<List<Country>>> {
        return object : NetworkBoundRepository<List<Country>>() {
            override suspend fun fetchFromRemote(): Response<List<Country>> =
                apiService.getAllCountriesData(sort)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllCountriesData(country: String, strict: Boolean): Flow<State<Country>> {
        return object : NetworkBoundRepository<Country>() {
            override suspend fun fetchFromRemote(): Response<Country> =
                apiService.getAllCountriesData(country, strict)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllStatesData(): Flow<State<List<CountryState>>> {
        return object : NetworkBoundRepository<List<CountryState>>() {
            override suspend fun fetchFromRemote(): Response<List<CountryState>> =
                apiService.getAllStatesData()
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getAllHistoricalData(): Flow<State<Timeline>> {
        return object : NetworkBoundRepository<Timeline>() {
            override suspend fun fetchFromRemote(): Response<Timeline> =
                apiService.getAllHistoricalData()
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getCountryHistoricalData(country: String): Flow<State<History>> {
        return object : NetworkBoundRepository<History>() {
            override suspend fun fetchFromRemote(): Response<History> =
                apiService.getCountryHistoricalData(country)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getNews(url: String): Flow<State<NewsResponse>> {
        return object : NetworkBoundRepository<NewsResponse>() {
            override suspend fun fetchFromRemote(): Response<NewsResponse> =
                apiService.getNews(url)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getWorldNews(url: String): Flow<State<NewsResponse>> {
        return object : WorldNewsBoundRepository() {
            override suspend fun fetchFromRemote(): MutableList<Article> =
                parser.getChannel(url).articles
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getFaqs(url: String): Flow<State<FaqResponse>> {
        return object : NetworkBoundRepository<FaqResponse>() {
            override suspend fun fetchFromRemote(): Response<FaqResponse> =
                apiService.getFaqs(url)
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getProtectiveMeasures(url: String): Flow<State<ProtectiveMeasures>> {
        return object : NetworkBoundRepository<ProtectiveMeasures>() {
            override suspend fun fetchFromRemote(): Response<ProtectiveMeasures> =
                apiService.getProtectiveMeasures(url)
        }.asFlow().flowOn(Dispatchers.IO)
    }
}