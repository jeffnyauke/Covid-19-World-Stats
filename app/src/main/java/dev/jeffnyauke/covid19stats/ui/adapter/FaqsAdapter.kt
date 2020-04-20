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
import dev.jeffnyauke.covid19stats.databinding.ItemFaqBinding
import dev.jeffnyauke.covid19stats.model.FaqsData

class FaqsAdapter :
    ListAdapter<FaqsData, FaqsAdapter.FaqsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FaqsViewHolder(
        ItemFaqBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FaqsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FaqsViewHolder(private val binding: ItemFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FaqsData) {
            binding.textQuestion.text = faq.question
            binding.textAnswer.text = faq.answer
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FaqsData>() {
            override fun areItemsTheSame(oldItem: FaqsData, newItem: FaqsData): Boolean =
                oldItem.question == newItem.question

            override fun areContentsTheSame(oldItem: FaqsData, newItem: FaqsData): Boolean =
                oldItem == newItem

        }
    }
}