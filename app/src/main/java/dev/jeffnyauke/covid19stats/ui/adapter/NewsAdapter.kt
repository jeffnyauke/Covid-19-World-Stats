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