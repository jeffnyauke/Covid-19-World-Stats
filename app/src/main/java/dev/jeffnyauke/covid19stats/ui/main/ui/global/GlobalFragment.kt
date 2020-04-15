package dev.jeffnyauke.covid19stats.ui.main.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.MergeAdapter
import dev.jeffnyauke.covid19stats.databinding.FragmentGlobalBinding
import dev.jeffnyauke.covid19stats.ui.main.MainViewModel
import dev.jeffnyauke.covid19stats.ui.main.adapter.ChartAdapter
import dev.jeffnyauke.covid19stats.ui.main.adapter.TotalAdapter
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class GlobalFragment : Fragment() {

    private lateinit var binding: FragmentGlobalBinding

    private val viewModel: MainViewModel by viewModel()

    private val mTotalAdapter = TotalAdapter()
    private val mChartAdapter = ChartAdapter()
    private val adapter = MergeAdapter(mTotalAdapter, mChartAdapter)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlobalBinding.inflate(inflater, container, false)

        binding.recycler.adapter = adapter

        initData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        return binding.root
    }

    private fun initData() {
        viewModel.covidGlobalData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    val item = state.data
                    mTotalAdapter.submitList(listOf(item))
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.covidAllHistoricalData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    val item = state.data
                    lifecycleScope.launch(Dispatchers.Main) {
                        delay(500)
                        mChartAdapter.submitList(listOf(item))
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        })

        loadData()
    }

    private fun loadData() {
        viewModel.getGlobalData()
        viewModel.getAllHistoricalData()
    }
}
