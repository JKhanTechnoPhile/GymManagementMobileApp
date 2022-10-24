package com.fitness.enterprise.management.subscription.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.enterprise.management.databinding.GymSubscriptionDetailsBinding
import com.fitness.enterprise.management.subscription.model.GymSubscription

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
            binding.gymSubscriptionFare.text = gymSubscriptionDetails.gymSubscriptionBaseFare
            binding.gymBranchName.text = gymSubscriptionDetails.gymBranch.gymName
            binding.gymBranchOtherDetails.text = gymSubscriptionDetails.gymBranch.gymFullAddress
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