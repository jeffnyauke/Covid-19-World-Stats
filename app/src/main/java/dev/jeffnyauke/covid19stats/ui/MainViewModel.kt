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

package dev.jeffnyauke.covid19stats.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jeffnyauke.covid19stats.api.Covid19StatsApiService
import dev.jeffnyauke.covid19stats.model.*
import dev.jeffnyauke.covid19stats.repository.CovidStatsRepository
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainViewModel(private val repository: CovidStatsRepository) : ViewModel() {

    private val _covidGlobalData = MutableLiveData<State<Global>>()
    val covidGlobalData: LiveData<State<Global>>
        get() = _covidGlobalData

    private val _covidAllCountriesData = MutableLiveData<State<List<Country>>>()
    val covidAllCountriesData: LiveData<State<List<Country>>>
        get() = _covidAllCountriesData

    private val _covidAllCountriesDataSearch = MutableLiveData<State<Country>>()
    val covidAllCountriesDataSearch: LiveData<State<Country>>
        get() = _covidAllCountriesDataSearch

    private val _covidAllStatesData = MutableLiveData<State<List<CountryState>>>()
    val covidAllStatesData: LiveData<State<List<CountryState>>>
        get() = _covidAllStatesData

    private val _covidCountryHistoricalData = MutableLiveData<State<History>>()
    val covidCountryHistoricalData: LiveData<State<History>>
        get() = _covidCountryHistoricalData

    private val _covidAllHistoricalData = MutableLiveData<State<Timeline>>()
    val covidAllHistoricalData: LiveData<State<Timeline>>
        get() = _covidAllHistoricalData

    private val _newsData = MutableLiveData<State<NewsResponse>>()
    val newsData: LiveData<State<NewsResponse>>
        get() = _newsData

    private val _faqsData = MutableLiveData<State<FaqResponse>>()
    val faqsData: LiveData<State<FaqResponse>>
        get() = _faqsData

    private val _protectiveMeasuresData = MutableLiveData<State<ProtectiveMeasures>>()
    val protectiveMeasuresData: LiveData<State<ProtectiveMeasures>>
        get() = _protectiveMeasuresData

    fun getGlobalData() {
        viewModelScope.launch {
            repository.getGlobalData().collect {
                _covidGlobalData.value = it
            }
        }
    }

    fun getAllCountriesData(sort: String) {
        viewModelScope.launch {
            repository.getAllCountriesData(sort).collect {
                _covidAllCountriesData.value = it
            }
        }
    }

    fun getAllCountriesData(country: String, strict: Boolean) {
        viewModelScope.launch {
            repository.getAllCountriesData(country, strict).collect {
                _covidAllCountriesDataSearch.value = it
            }
        }
    }

    fun getAllStatesData() {
        viewModelScope.launch {
            repository.getAllStatesData().collect {
                _covidAllStatesData.value = it
            }
        }
    }

    fun getCountryHistoricalData(country: String) {
        viewModelScope.launch {
            repository.getCountryHistoricalData(country).collect {
                _covidCountryHistoricalData.value = it
            }
        }
    }

    fun getAllHistoricalData() {
        viewModelScope.launch {
            repository.getAllHistoricalData().collect {
                _covidAllHistoricalData.value = it
            }
        }
    }

    fun getNewsData() {
        viewModelScope.launch {
            repository.getNews(Covid19StatsApiService.NEWS_URL).collect {
                _newsData.value = it
            }
        }
    }

    fun getFaqsData() {
        viewModelScope.launch {
            repository.getFaqs(Covid19StatsApiService.FAQS_URL).collect {
                _faqsData.value = it
            }
        }
    }

    fun getProtectiveMeasuresData() {
        viewModelScope.launch {
            repository.getProtectiveMeasures(Covid19StatsApiService.PROTECTIVE_MEASURES_URL)
                .collect {
                    _protectiveMeasuresData.value = it
                }
        }
    }
}