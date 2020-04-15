package dev.jeffnyauke.covid19stats.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _covidAllStatesData = MutableLiveData<State<List<CountryState>>>()
    val covidAllStatesData: LiveData<State<List<CountryState>>>
        get() = _covidAllStatesData

    private val _covidCountryHistoricalData = MutableLiveData<State<History>>()
    val covidCountryHistoricalData: LiveData<State<History>>
        get() = _covidCountryHistoricalData

    private val _covidAllHistoricalData = MutableLiveData<State<Timeline>>()
    val covidAllHistoricalData: LiveData<State<Timeline>>
        get() = _covidAllHistoricalData

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
}