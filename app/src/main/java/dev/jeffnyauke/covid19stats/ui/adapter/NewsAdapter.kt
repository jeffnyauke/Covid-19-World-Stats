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
import dev.jeffnyauke.covid19stats.databinding.ItemNewsBinding
import dev.jeffnyauke.covid19stats.model.NewsData

class NewsAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<NewsData, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
        ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClickListener)

    interface OnItemClickListener {
        fun onItemClicked(post: NewsData)
    }

    class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsData, onItemClickListener: OnItemClickListener? = null) {
            binding.postTitle.text = news.title
            binding.postDate.text = news.date
            binding.imageView.load(R.drawable.corona) {
                crossfade(true)
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }

            onItemClickListener?.let { listener ->
                binding.root.setOnClickListener {
                    listener.onItemClicked(news)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsData>() {
            override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean =
                oldItem == newItem

        }
    }
}