package dev.jeffnyauke.covid19stats.api

import dev.jeffnyauke.covid19stats.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19StatsApiService {

    @GET("/all")
    suspend fun getGlobalData(): Response<Global>

    @GET("/countries")
    suspend fun getAllCountriesData(): Response<List<Country>>

    @GET("/states")
    suspend fun getAllStatesData(): Response<List<CountryState>>

    @GET("/v2/historical/all")
    suspend fun getAllHistoricalData(): Response<Timeline>

    @GET("/v2/historical/{country}")
    suspend fun getCountryHistoricalData(@Path("country") country: String): Response<History>

    companion object {
        const val BASE_URL = "https://corona.lmao.ninja/"
    }
}
