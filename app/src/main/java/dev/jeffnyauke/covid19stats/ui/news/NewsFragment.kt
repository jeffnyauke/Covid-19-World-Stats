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

package dev.jeffnyauke.covid19stats.ui.news

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.MergeAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.FragmentNewsBinding
import dev.jeffnyauke.covid19stats.model.NewsData
import dev.jeffnyauke.covid19stats.ui.MainViewModel
import dev.jeffnyauke.covid19stats.ui.adapter.NewsAdapter
import dev.jeffnyauke.covid19stats.ui.analytics.Tracker
import dev.jeffnyauke.covid19stats.ui.analytics.events.NewsClickEvent
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.get
import dev.jeffnyauke.covid19stats.utils.PreferenceHelper.set
import dev.jeffnyauke.covid19stats.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class NewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNewsBinding

    private val viewModel: MainViewModel by viewModel()
    private val prefs: SharedPreferences by inject()
    private val tracker: Tracker by inject()
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    private val mNewsAdapter = NewsAdapter(onItemClickListener = this)
    private val adapter = MergeAdapter(mNewsAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.recycler.adapter = adapter

        initData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.buttonWorld -> {
                        prefs[getString(R.string.pref_news_type)] = 0
                        loadData()
                    }

                    R.id.buttonWho -> {
                        prefs[getString(R.string.pref_news_type)] = 1
                        loadData()
                    }
                }
            }
        }

        firebaseAnalytics.setCurrentScreen(requireActivity(), "News Fragment", null)
        return binding.root
    }

    private fun initData() {
        viewModel.newsData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    val list = state.data
                    mNewsAdapter.submitList(list.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        loadData()
    }

    private fun loadData() {
        if (prefs[getString(R.string.pref_news_type), 0] == 0) {
            viewModel.getWorldNewsData()
            binding.toggleButton.check(R.id.buttonWorld)
        } else {
            viewModel.getNewsData()
            binding.toggleButton.check(R.id.buttonWho)
        }
    }

    override fun onItemClicked(post: NewsData) {
        post.link?.let {
            val variant = when {
                prefs[getString(R.string.pref_news_type), 0] == 0 -> {
                    NewsClickEvent.ClickVariant.WORLD
                }
                else -> {
                    NewsClickEvent.ClickVariant.WHO
                }
            }
            tracker.track(NewsClickEvent(post.title.toString(), variant))
            openWebPage(it)
        }
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_faqs -> {
                val action = NewsFragmentDirections
                    .actionNewsFragmentToFaqsFragment()
                NavHostFragment.findNavController(this@NewsFragment)
                    .navigate(action)
                true
            }
            R.id.action_settings -> {
                val action = NewsFragmentDirections
                    .actionNewsFragmentToSettingsFragment()
                NavHostFragment.findNavController(this@NewsFragment)
                    .navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
