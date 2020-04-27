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

package dev.jeffnyauke.covid19stats.ui.countries

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.MergeAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.FragmentCountryBinding
import dev.jeffnyauke.covid19stats.model.Country
import dev.jeffnyauke.covid19stats.model.enums.Order
import dev.jeffnyauke.covid19stats.ui.MainViewModel
import dev.jeffnyauke.covid19stats.ui.adapter.CountryAdapter
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker
import dev.jeffnyauke.covid19stats.ui.analytics.events.CountryClickEvent
import dev.jeffnyauke.covid19stats.ui.analytics.events.SearchClickEvent
import dev.jeffnyauke.covid19stats.ui.analytics.events.SortEvent
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.get
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CountryFragment : Fragment(), CountryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCountryBinding

    private val viewModel: MainViewModel by viewModel()
    private val prefs: SharedPreferences by inject()
    private val tracker: Tracker by inject()
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    private val mCountryAdapter = CountryAdapter(onItemClickListener = this)
    private val adapter = MergeAdapter(mCountryAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        binding.recycler.adapter = adapter

        initData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        firebaseAnalytics.setCurrentScreen(requireActivity(), "Country Fragment", null)

        return binding.root
    }

    private fun initData() {
        viewModel.covidAllCountriesData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    val list = state.data
                    mCountryAdapter.submitList(list)
                    binding.swipeRefreshLayout.isRefreshing = false
                    scrollToTop()
                }
            }
        })
        viewModel.covidAllCountriesDataSearch.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> binding.swipeRefreshLayout.isRefreshing = false
                is State.Success -> {
                    val item = state.data
                    mCountryAdapter.submitList(listOf(item))
                    binding.swipeRefreshLayout.isRefreshing = false
                    scrollToTop()
                }
            }
        })

        loadData()
    }

    private fun loadData() {
        viewModel.getAllCountriesData(prefs[getString(R.string.pref_sort), Order.CASES.tag]!!)
    }

    override fun onItemClicked(country: Country) {
        tracker.track(CountryClickEvent(country.country.toString()))
        val action = CountryFragmentDirections
            .actionCountryFragmentToCountryDetailsFragment(country)
        NavHostFragment.findNavController(this@CountryFragment)
            .navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_countries_fragment, menu)

        var queryTextChangedJob: Job? = null
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getAllCountriesData(query, false)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.length > 1) {
                        queryTextChangedJob?.cancel()

                        queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                            delay(500)
                            viewModel.getAllCountriesData(newText, false)
                        }
                    }
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                tracker.track(SearchClickEvent("Country"))
                true
            }
            R.id.action_sort -> {
                displayDialog()
                true
            }
            R.id.action_faqs -> {
                val action = CountryFragmentDirections
                    .actionCountryFragmentToFaqsFragment()
                NavHostFragment.findNavController(this@CountryFragment)
                    .navigate(action)
                true
            }
            R.id.action_settings -> {
                val action = CountryFragmentDirections
                    .actionCountryFragmentToSettingsFragment()
                NavHostFragment.findNavController(this@CountryFragment)
                    .navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayDialog() {
        val listItemsDesc = Order.values().map { it.desc }.toTypedArray()
        val listItemsNames = Order.values().map { it.name }.toTypedArray()
        val listItemsTags = Order.values().map { it.tag }.toTypedArray()
        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle("Sort according to:")
        mBuilder.setSingleChoiceItems(
            listItemsDesc,
            listItemsTags.indexOf(prefs[getString(R.string.pref_sort), Order.CASES.tag]!!)
        ) { dialogInterface, i ->
            prefs[getString(R.string.pref_sort)] = Order.valueOf(listItemsNames[i]).tag
            loadData()
            tracker.track(SortEvent(prefs[getString(R.string.pref_sort), Order.CASES.tag].toString()))
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun scrollToTop() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            binding.recycler.scrollToPosition(0)
        }
    }
}
