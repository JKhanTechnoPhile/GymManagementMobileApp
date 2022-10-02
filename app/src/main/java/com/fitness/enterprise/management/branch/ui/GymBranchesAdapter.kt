package com.fitness.enterprise.management.branch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.databinding.GymBranchDetailsBinding

class GymBranchesAdapter(private val onGymBranchClicked: (GymBranch) -> Unit) : ListAdapter<GymBranch, GymBranchesAdapter.GymBranchViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymBranchViewHolder {
        val binding = GymBranchDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GymBranchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GymBranchViewHolder, position: Int) {
        val gymBranchDetails = getItem(position)
        gymBranchDetails?.let {
            holder.bind(it)
        }
    }

    inner class GymBranchViewHolder(private val binding: GymBranchDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gymBranchDetails: GymBranch) {
            binding.gymBranchName.text = gymBranchDetails.gymName
            binding.gymBranchOtherDetails.text = gymBranchDetails.gymFullAddress
            binding.root.setOnClickListener {
                onGymBranchClicked(gymBranchDetails)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<GymBranch>() {
        override fun areItemsTheSame(oldItem: GymBranch, newItem: GymBranch): Boolean {
            return oldItem.gymBranchId == newItem.gymBranchId
        }

        override fun areContentsTheSame(oldItem: GymBranch, newItem: GymBranch): Boolean {
            return oldItem == newItem
        }
    }
}