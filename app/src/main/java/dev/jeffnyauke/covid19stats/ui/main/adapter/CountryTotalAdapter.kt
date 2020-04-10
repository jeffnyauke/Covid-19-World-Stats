package dev.jeffnyauke.covid19stats.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jeffnyauke.covid19stats.R
import dev.jeffnyauke.covid19stats.databinding.ItemTotalBinding
import dev.jeffnyauke.covid19stats.model.Country
import dev.jeffnyauke.covid19stats.utils.getFormattedNumber
import dev.jeffnyauke.covid19stats.utils.getPeriod
import java.util.*

class CountryTotalAdapter :
    ListAdapter<Country, CountryTotalAdapter.TotalViewHolder>(DIFF_CALLBACK) {

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
        fun bind(country: Country) {
            binding.confirmedLayout.requestFocus()
            binding.textLastUpdatedView.text = itemView.context.getString(
                R.string.text_last_updated,
                getPeriod(
                    Date(country.updated)
                )
            )

            binding.textConfirmed.text = getFormattedNumber(country.cases)
            binding.textActive.text = getFormattedNumber(country.active)
            binding.textRecovered.text = getFormattedNumber(country.recovered)
            binding.textDeceased.text = getFormattedNumber(country.deaths)


            binding.textTodayCases.text =
                "${getFormattedNumber(country.todayCases.toDouble() / (country.cases.toDouble() - country.todayCases.toDouble()) * 100)}% ${getFormattedNumber(
                    country.todayCases
                )}"
            binding.textTodayDeaths.text =
                "${getFormattedNumber(country.todayDeaths.toDouble() / (country.deaths.toDouble() - country.todayDeaths.toDouble()) * 100)}% ${getFormattedNumber(
                    country.todayDeaths
                )}"
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