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

package dev.jeffnyauke.covid19stats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.ItemTotalBinding
import dev.jeffnyauke.covid19stats.model.Global
import dev.jeffnyauke.covid19stats.utils.getFormattedNumber
import dev.jeffnyauke.covid19stats.utils.getPeriod
import java.util.*

class TotalAdapter : ListAdapter<Global, TotalAdapter.TotalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TotalViewHolder(
        ItemTotalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) =
        holder.bind(getItem(position))


    class TotalViewHolder(private val binding: ItemTotalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(global: Global) {
            binding.textLastUpdatedView.text = itemView.context.getString(
                R.string.text_last_updated,
                getPeriod(
                    Date(global.updated)
                )
            )

            binding.textConfirmed.text = getFormattedNumber(global.cases)
            binding.textActive.text = getFormattedNumber(global.active)
            binding.textRecovered.text = getFormattedNumber(global.recovered)
            binding.textDeceased.text = getFormattedNumber(global.deaths)

            binding.textTodayCases.text =
                "${getFormattedNumber(global.todayCases.toDouble() / (global.cases.toDouble() - global.todayCases.toDouble()) * 100)}% ${getFormattedNumber(
                    global.todayCases
                )}"
            binding.textTodayDeaths.text =
                "${getFormattedNumber(global.todayDeaths.toDouble() / (global.deaths.toDouble() - global.todayDeaths.toDouble()) * 100)}% ${getFormattedNumber(
                    global.todayDeaths
                )}"

            val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Global>() {
            override fun areItemsTheSame(oldItem: Global, newItem: Global): Boolean =
                oldItem.updated == newItem.updated

            override fun areContentsTheSame(oldItem: Global, newItem: Global): Boolean =
                oldItem == newItem

        }
    }
}