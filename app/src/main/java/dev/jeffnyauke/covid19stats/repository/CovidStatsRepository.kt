package dev.jeffnyauke.covid19stats.repository

import dev.jeffnyauke.covid19stats.api.Covid19StatsApiService
import dev.jeffnyauke.covid19stats.model.*
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class CovidStatsRepository(private val apiService: Covid19StatsApiService) {

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
}