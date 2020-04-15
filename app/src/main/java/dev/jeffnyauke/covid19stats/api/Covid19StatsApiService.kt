package dev.jeffnyauke.covid19stats.api

import dev.jeffnyauke.covid19stats.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    companion object {
        const val BASE_URL = "https://corona.lmao.ninja/"
    }
}
