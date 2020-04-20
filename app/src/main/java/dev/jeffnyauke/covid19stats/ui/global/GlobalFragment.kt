package dev.jeffnyauke.covid19stats.ui.global

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.MergeAdapter
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.FragmentGlobalBinding
import dev.jeffnyauke.covid19stats.ui.MainViewModel
import dev.jeffnyauke.covid19stats.ui.adapter.ChartAdapter
import dev.jeffnyauke.covid19stats.ui.adapter.TotalAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_faqs -> {
                val action = GlobalFragmentDirections
                    .actionGlobalFragmentToFaqsFragment()
                NavHostFragment.findNavController(this@GlobalFragment)
                    .navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
