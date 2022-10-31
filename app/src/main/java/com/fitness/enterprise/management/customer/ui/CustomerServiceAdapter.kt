package com.fitness.enterprise.management.customer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.ui.CustomerServiceAdapter.CustomerServiceViewHolder
import com.fitness.enterprise.management.databinding.CustomerServiceDetailsBinding
import com.fitness.enterprise.management.utils.Constants

class CustomerServiceAdapter(private val onCustomerClicked: (CustomerDetails) -> Unit) : ListAdapter<CustomerDetails, CustomerServiceViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerServiceViewHolder {
        val binding = CustomerServiceDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerServiceViewHolder, position: Int) {
        val gymBranchDetails = getItem(position)
        gymBranchDetails?.let {
            holder.bind(it)
        }
    }

    inner class CustomerServiceViewHolder(private val binding: CustomerServiceDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(customerDetails: CustomerDetails) {
            binding.customerName.text = HtmlCompat.fromHtml("<b>Customer Name:</b> ${customerDetails.customerName}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            customerDetails.customerName
            binding.customerContact.text = HtmlCompat.fromHtml("<b>Customer Contact:</b> ${customerDetails.customerPhoneNumber}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            customerDetails.customerPhoneNumber
            binding.customerStatus.text = HtmlCompat.fromHtml("<b>Customer Status:</b> ${Constants.getCustomerStatus(customerDetails.customerStatus)}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.root.setOnClickListener {
                onCustomerClicked(customerDetails)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<CustomerDetails>() {
        override fun areItemsTheSame(oldItem: CustomerDetails, newItem: CustomerDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CustomerDetails, newItem: CustomerDetails): Boolean {
            return oldItem == newItem
        }
    }
}