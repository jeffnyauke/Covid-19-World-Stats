package dev.jeffnyauke.covid19stats.ui.main.adapter

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
            binding.textState.text = "${absoluteAdapterPosition + 1}. ${country.country}"
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
                placeholder(R.color.white)
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