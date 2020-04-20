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