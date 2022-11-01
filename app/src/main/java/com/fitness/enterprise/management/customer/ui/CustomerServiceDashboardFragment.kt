package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.viewmodel.CustomerServiceDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentCustomerServiceDashboardBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.CustomerServiceEnum
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerServiceDashboardFragment : Fragment() {

    private var _binding: FragmentCustomerServiceDashboardBinding? = null
    private val binding get() = _binding!!

    private val customerServiceDashboardViewModel by viewModels<CustomerServiceDashboardViewModel>()

    private lateinit var customerServiceAdapter: CustomerServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Customer Service"
        }
        _binding = FragmentCustomerServiceDashboardBinding.inflate(inflater, container, false)
        customerServiceAdapter = CustomerServiceAdapter(::onCustomerClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        customerServiceDashboardViewModel.getAllCustomers()
        binding.customerServiceDashboard.layoutManager = LinearLayoutManager(requireContext())//StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.customerServiceDashboard.adapter = customerServiceAdapter
        binding.createCustomerService.setOnClickListener {
//            findNavController().navigate(R.id.customerEnquiryFragment)
            val customerEnquiryBottomSheet = CustomerEnquiryBottomSheet.newInstance(customerEnquiryCallback)
            customerEnquiryBottomSheet.isCancelable = false
            customerEnquiryBottomSheet.show(childFragmentManager, CustomerEnquiryBottomSheet.TAG)
        }
    }

    private val customerEnquiryCallback : (CustomerDetails) -> Unit = {
        navigateToCustomerOnBoardingFragment(it)
    }

    private fun navigateToCustomerOnBoardingFragment(customerDetails: CustomerDetails) {
        Log.d(Constants.TAG, "CustomerDetails: $customerDetails")
        val bundle = Bundle()
        bundle.putString("customerDetails", Gson().toJson(customerDetails))
        findNavController().navigate(R.id.action_customerEnquiryFragment_to_customerOnboardingFragment, bundle)
    }

    private fun navigateToCustomerPlanBreakupFragment(customerDetails: CustomerDetails) {
        Log.d(Constants.TAG, "CustomerDetails: $customerDetails")
        val bundle = Bundle()
        bundle.putString("customerDetails", Gson().toJson(customerDetails))
        findNavController().navigate(R.id.action_customerServiceDashboardFragment_to_customerPlanBreakUpFragment, bundle)
    }

    private fun bindObservers() {
        customerServiceDashboardViewModel.customersData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    customerServiceAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "Customer Service",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onCustomerClicked(customerDetails: CustomerDetails) {
        when(customerDetails.customerStatus) {
            CustomerServiceEnum.CUSTOMER_ENQUIRED.getUserRoleAsStringForServer() -> {
                navigateToCustomerOnBoardingFragment(customerDetails)
            }
            CustomerServiceEnum.CUSTOMER_REGISTERED.getUserRoleAsStringForServer() -> {
                navigateToCustomerPlanBreakupFragment(customerDetails)
            }
            CustomerServiceEnum.CUSTOMER_PLAN_ACTIVE.getUserRoleAsStringForServer() -> {
                //TODO: Show plan customer and plan details
            }
            CustomerServiceEnum.CUSTOMER_PLAN_INACTIVE.getUserRoleAsStringForServer() -> {
                navigateToCustomerPlanBreakupFragment(customerDetails)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}