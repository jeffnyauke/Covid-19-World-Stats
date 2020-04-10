package dev.jeffnyauke.covid19stats.ui.main.ui.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.MergeAdapter
import dev.jeffnyauke.covid19stats.databinding.FragmentCountryBinding
import dev.jeffnyauke.covid19stats.model.Country
import dev.jeffnyauke.covid19stats.ui.main.MainViewModel
import dev.jeffnyauke.covid19stats.ui.main.adapter.CountryAdapter
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CountryFragment : Fragment(), CountryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCountryBinding

    private val viewModel: MainViewModel by viewModel()

    private val mCountryAdapter = CountryAdapter(onItemClickListener = this)
    private val adapter = MergeAdapter(mCountryAdapter)

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
                }
            }
        })

        loadData()
    }

    private fun loadData() {
        viewModel.getAllCountriesData()
    }

    override fun onItemClicked(country: Country) {
        val action = CountryFragmentDirections
            .actionCountryFragmentToCountryDetailsFragment(country)
        NavHostFragment.findNavController(this@CountryFragment)
            .navigate(action)
    }
}
