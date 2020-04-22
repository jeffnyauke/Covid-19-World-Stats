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

import androidx.annotation.MainThread
import com.prof.rssparser.Article
import dev.jeffnyauke.covid19stats.model.NewsData
import dev.jeffnyauke.covid19stats.model.NewsResponse
import dev.jeffnyauke.covid19stats.utils.State
import dev.jeffnyauke.covid19stats.utils.getPeriodWorldNews
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
abstract class WorldNewsBoundRepository {

    fun asFlow() = flow<State<NewsResponse>> {

        // Emit Loading World News
        emit(State.loading())

        try {
            // Fetch latest data from remote
            val apiResponse = fetchFromRemote()

            if (apiResponse.isNullOrEmpty()) {
                // Something went wrong! Emit Error state.
                emit(State.error("Something went wrong! Can't get latest data."))
            } else {
                // Success
                emit(State.success(NewsResponse(data = apiResponse.map {
                    NewsData(
                        title = it.title,
                        link = it.link,
                        date = "${getPeriodWorldNews(it.pubDate!!)} | ${it.sourceName}",
                        image = it.image
                    )
                })))
            }


        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(State.error("Network error! Can't get latest data."))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): MutableList<Article>
}