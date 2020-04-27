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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.MergeAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.databinding.FragmentCountryDetailsBinding
import dev.jeffnyauke.covid19stats.ui.MainActivity
import dev.jeffnyauke.covid19stats.ui.MainViewModel
import dev.jeffnyauke.covid19stats.ui.adapter.CountryChartAdapter
import dev.jeffnyauke.covid19stats.ui.adapter.CountryHeaderAdapter
import dev.jeffnyauke.covid19stats.ui.adapter.CountryTotalAdapter
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CountryDetailsFragment : Fragment() {

    private val args: CountryDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCountryDetailsBinding

    private val viewModel: MainViewModel by viewModel()
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    private val mTotalAdapter = CountryTotalAdapter()
    private val mChartAdapter = CountryChartAdapter()
    private val mCountryHeaderAdapter = CountryHeaderAdapter()
    private val adapter = MergeAdapter(mCountryHeaderAdapter, mTotalAdapter, mChartAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)

        binding.recycler.adapter = adapter

        (activity as MainActivity).supportActionBar?.title = args.country.country
        initData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        firebaseAnalytics.setCurrentScreen(requireActivity(), "Country Details Fragment", null)
        return binding.root
    }

    private fun initData() {
        mCountryHeaderAdapter.submitList(listOf(args.country))
        mTotalAdapter.submitList(listOf(args.country))
        viewModel.covidCountryHistoricalData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    val item = state.data
                    mChartAdapter.submitList(listOf(item))
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        loadData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavHostFragment.findNavController(this)
                .navigateUp()
        }
        return true
    }

    private fun loadData() {
        viewModel.getCountryHistoricalData(args.country.country.toString())
    }

}
