package com.fitness.enterprise.management.dashboard.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.ui.GymBranchDashboardActivity
import com.fitness.enterprise.management.common.ui.generic.recyclerview.GenericListAdapter
import com.fitness.enterprise.management.customer.ui.CustomerServiceDashboardActivity
import com.fitness.enterprise.management.dashboard.ui.model.GenericListAdapterDataClass
import com.fitness.enterprise.management.databinding.FragmentUserServicesBinding
import com.fitness.enterprise.management.subscription.ui.GymSubscriptionDashboardActivity
import com.fitness.enterprise.management.utils.DashboardServicesEnum
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
                    holder.itemView.rootView.setOnClickListener {
//                        AlertDialog.showAlert(
//                            requireContext(),
//                            "Services",
//                            "Selected service: ${item.serviceName}",
//                            positiveButtonText = "OK"
//                        )
                        when(item.enum) {
                            DashboardServicesEnum.DASHBOARD_SERVICE_CUSTOMER -> {
                                requireActivity().startActivity(Intent(requireActivity(), CustomerServiceDashboardActivity::class.java))
                            }
                            DashboardServicesEnum.DASHBOARD_SERVICE_GYM_BRANCH -> {
                                requireActivity().startActivity(Intent(requireActivity(), GymBranchDashboardActivity::class.java))
                            }
                            DashboardServicesEnum.DASHBOARD_SERVICE_GYM_SUBSCRIPTION -> {
                                requireActivity().startActivity(Intent(requireActivity(), GymSubscriptionDashboardActivity::class.java))
                            }
                            DashboardServicesEnum.DASHBOARD_SERVICE_BRANCH_ADMIN -> {

                            }
                        }
                    }
                }){}.apply {
                submitList(getDashboardServiceTiles())
//                    listOf(
//                        GenericListAdapterDataClass("Gym Branch Services", "Facilitate to register new or edit or view gym branch."),
//                        GenericListAdapterDataClass("Gym Subscription Services", "Facilitate to add new or edit or view gym subscription plans."),
//                        GenericListAdapterDataClass("Branch Admin Services", "Facilitate to register new or edit or view branch admin.")
//                    ))
            }
        }
    }

    private fun getDashboardServiceTiles(): List<GenericListAdapterDataClass> {
        val dashboardServicesTilesAsList = DashboardServicesEnum.values()
        val dashboardServicesTiles = dashboardServicesTilesAsList.map { userRoleEnum -> GenericListAdapterDataClass(userRoleEnum.getServiceName(), userRoleEnum.getServiceDescription(), userRoleEnum) }
        return dashboardServicesTiles
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}