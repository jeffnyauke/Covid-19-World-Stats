package dev.jeffnyauke.covid19stats.ui.main.ui.countries

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
import dev.jeffnyauke.covid19stats.databinding.FragmentCountryDetailsBinding
import dev.jeffnyauke.covid19stats.ui.main.MainActivity
import dev.jeffnyauke.covid19stats.ui.main.MainViewModel
import dev.jeffnyauke.covid19stats.ui.main.adapter.CountryChartAdapter
import dev.jeffnyauke.covid19stats.ui.main.adapter.CountryHeaderAdapter
import dev.jeffnyauke.covid19stats.ui.main.adapter.CountryTotalAdapter
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CountryDetailsFragment : Fragment() {

    private val args: CountryDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentCountryDetailsBinding

    private val viewModel: MainViewModel by viewModel()

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
