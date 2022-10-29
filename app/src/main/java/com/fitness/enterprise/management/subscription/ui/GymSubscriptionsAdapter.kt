package com.fitness.enterprise.management.subscription.ui

import android.R.attr.name
import android.R.id
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.enterprise.management.databinding.GymSubscriptionDetailsBinding
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.utils.Constants


class GymSubscriptionsAdapter(private val onGymSubscriptionClicked: (GymSubscription) -> Unit) : ListAdapter<GymSubscription, GymSubscriptionsAdapter.GymSubscriptionViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymSubscriptionsAdapter.GymSubscriptionViewHolder {
        val binding = GymSubscriptionDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GymSubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GymSubscriptionsAdapter.GymSubscriptionViewHolder, position: Int) {
        val gymBranchDetails = getItem(position)
        gymBranchDetails?.let {
            holder.bind(it)
        }
    }

    inner class GymSubscriptionViewHolder(private val binding: GymSubscriptionDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gymSubscriptionDetails: GymSubscription) {
            binding.gymSubscriptionName.text = gymSubscriptionDetails.gymSubscriptionName
            binding.gymSubscriptionFrequency.text = gymSubscriptionDetails.gymSubscriptionFrequency

            val basePriceString = "<b>Base Price: ${Constants.INR_CURRENCY}</b> ${Constants.currencyFormat(gymSubscriptionDetails.gymSubscriptionBaseFare)}"
            binding.gymSubscriptionFare.text = HtmlCompat.fromHtml(basePriceString, HtmlCompat.FROM_HTML_MODE_LEGACY)

            binding.gymBranchName.text = HtmlCompat.fromHtml("<b>Gym:</b> ${gymSubscriptionDetails.gymBranch.gymName}", HtmlCompat.FROM_HTML_MODE_LEGACY)

            binding.gymBranchOtherDetails.text = HtmlCompat.fromHtml("<b>Gym Address:</b> ${gymSubscriptionDetails.gymBranch.gymFullAddress}", HtmlCompat.FROM_HTML_MODE_LEGACY)

            binding.root.setOnClickListener {
                onGymSubscriptionClicked(gymSubscriptionDetails)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<GymSubscription>() {
        override fun areItemsTheSame(oldItem: GymSubscription, newItem: GymSubscription): Boolean {
            return oldItem.gymSubscriptionId == newItem.gymSubscriptionId
        }

        override fun areContentsTheSame(oldItem: GymSubscription, newItem: GymSubscription): Boolean {
            return oldItem == newItem
        }
    }
}