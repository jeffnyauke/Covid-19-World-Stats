package dev.jeffnyauke.covid19stats.ui.main.ui.countries

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
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.FragmentCountryBinding
import dev.jeffnyauke.covid19stats.model.Country
import dev.jeffnyauke.covid19stats.model.enums.Order
import dev.jeffnyauke.covid19stats.ui.main.MainViewModel
import dev.jeffnyauke.covid19stats.ui.main.adapter.CountryAdapter
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.get
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CountryFragment : Fragment(), CountryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCountryBinding

    private val viewModel: MainViewModel by viewModel()

    private val mCountryAdapter = CountryAdapter(onItemClickListener = this)
    private val adapter = MergeAdapter(mCountryAdapter)
    private lateinit var prefs: SharedPreferences

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
        prefs = PreferenceHelper.defaultPrefs(requireContext())
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
        viewModel.getAllCountriesData(prefs[Constants.PREFERENCE_SORT, Order.CASES.tag]!!)
    }

    override fun onItemClicked(country: Country) {
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
                true
            }
            R.id.action_sort -> {
                displayDialog()
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
            listItemsTags.indexOf(prefs[Constants.PREFERENCE_SORT, Order.CASES.tag]!!)
        ) { dialogInterface, i ->
            prefs[Constants.PREFERENCE_SORT] = Order.valueOf(listItemsNames[i]).tag
            loadData()
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

    object Constants {
        const val PREFERENCE_SORT = "PREFERENCE_SORT"
    }
}
