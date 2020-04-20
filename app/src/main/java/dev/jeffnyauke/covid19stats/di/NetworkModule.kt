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

package dev.jeffnyauke.covid19stats.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.jeffnyauke.covid19stats.api.Covid19StatsApiService
import dev.jeffnyauke.covid19stats.repository.CovidStatsRepository
import dev.jeffnyauke.covid19stats.utils.isNetworkAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Covid19StatsApiService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(Covid19StatsApiService::class.java)
    }

    single {
        CovidStatsRepository(get())
    }
}

fun getOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (isNetworkAvailable(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            chain.proceed(request)
        }
        .build()
}