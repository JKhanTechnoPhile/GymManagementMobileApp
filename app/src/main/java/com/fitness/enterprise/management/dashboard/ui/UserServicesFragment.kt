package com.fitness.enterprise.management.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.common.ui.generic.recyclerview.GenericListAdapter
import com.fitness.enterprise.management.dashboard.ui.model.GenericListAdapterDataClass
import com.fitness.enterprise.management.databinding.FragmentUserServicesBinding
import com.google.android.material.textview.MaterialTextView

class UserServicesFragment : Fragment() {

    private var _binding: FragmentUserServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userServicesDashboard.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = object : GenericListAdapter<GenericListAdapterDataClass>(R.layout.user_services_item,
                bind = { item, holder, itemCount ->
                    with(holder.itemView) {
                        this.findViewById<MaterialTextView>(R.id.user_service_name).text = item.serviceName
                        this.findViewById<MaterialTextView>(R.id.user_service_description).text = item.serviceShortDescription
                    }
                }){}.apply {
                submitList(
                    listOf(
                        GenericListAdapterDataClass("Gym Branch Service", "Facilitate to register new or edit or view gym branch."),
                        GenericListAdapterDataClass("Branch Admin Services", "Facilitate to register new or edit or view branch admin.")
                    ))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}