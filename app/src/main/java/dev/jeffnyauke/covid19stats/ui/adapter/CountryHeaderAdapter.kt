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
import dev.jeffnyauke.covid19stats.databinding.ItemCountryHeaderBinding
import dev.jeffnyauke.covid19stats.model.Country

class CountryHeaderAdapter :
    ListAdapter<Country, CountryHeaderAdapter.CountryHeaderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryHeaderViewHolder(
        ItemCountryHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CountryHeaderViewHolder, position: Int) =
        holder.bind(getItem(position))


    class CountryHeaderViewHolder(private val binding: ItemCountryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.textState.text = country.country
            binding.imageFlag.load(country.countryInfo?.flag) {
                crossfade(true)
                placeholder(R.color.windowBackground)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.updated == newItem.updated

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem

        }
    }
}