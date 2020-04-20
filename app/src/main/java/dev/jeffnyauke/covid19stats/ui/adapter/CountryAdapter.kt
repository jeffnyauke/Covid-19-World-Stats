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
import coil.api.load
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.ItemCountryBinding
import dev.jeffnyauke.covid19stats.model.Country
import dev.jeffnyauke.covid19stats.utils.getFormattedNumber
import dev.jeffnyauke.covid19stats.utils.getPeriod
import java.util.*

class CountryAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Country, CountryAdapter.StateViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StateViewHolder(
        ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClickListener)

    interface OnItemClickListener {
        fun onItemClicked(country: Country)
    }

    class StateViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, onItemClickListener: OnItemClickListener? = null) {
            binding.textState.text = "${bindingAdapterPosition + 1}. ${country.country}"
            binding.textLastUpdatedView.text = itemView.context.getString(
                R.string.text_last_updated,
                getPeriod(
                    Date(country.updated)
                )
            )

            binding.textConfirmed.text = getFormattedNumber(country.cases)
            binding.textActive.text = getFormattedNumber(country.active)
            binding.textRecovered.text = getFormattedNumber(country.recovered)
            binding.textDeath.text = getFormattedNumber(country.deaths)

            binding.imageFlag.load(country.countryInfo?.flag) {
                crossfade(true)
                placeholder(R.color.windowBackground)
                error(R.color.windowBackground)
            }

            onItemClickListener?.let { listener ->
                binding.root.setOnClickListener {
                    listener.onItemClicked(country)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.country == newItem.country

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem

        }
    }
}