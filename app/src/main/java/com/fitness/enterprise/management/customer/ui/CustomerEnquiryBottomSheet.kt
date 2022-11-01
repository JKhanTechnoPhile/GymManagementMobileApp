package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.viewmodel.CustomerServiceDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentCustomerEnquiryBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.CustomerServiceEnum
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


//https://www.simplifiedcoding.net/bottom-sheet-android/
//https://m2.material.io/components/sheets-bottom/android#using-bottom-sheets
@AndroidEntryPoint
class CustomerEnquiryBottomSheet : BottomSheetDialogFragment() {

    private val customerServiceDashboardViewModel by viewModels<CustomerServiceDashboardViewModel>()

    companion object {
        const val TAG = "CustomerEnquiryBottomSheet"
        private lateinit var customerEnquiryCallback: (CustomerDetails) -> Unit

        fun newInstance(customerEnquiryCallback : (CustomerDetails) -> Unit): CustomerEnquiryBottomSheet {
            val fragment = CustomerEnquiryBottomSheet()
            this.customerEnquiryCallback = customerEnquiryCallback
            return fragment
        }
    }

    private var _binding: FragmentCustomerEnquiryBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        //this forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentCustomerEnquiryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dismissButton.setOnClickListener {
            dismiss()
        }

        binding.customerEnquireButton.setOnClickListener {

            val customerName = binding.customerNameTextField.editText?.text.toString()
            val customerContact = binding.customerPhoneNumberTextField.editText?.text.toString()
            val customerEmail = binding.customerEmailIdTextField.editText?.text.toString()

            binding.customerNameTextField.editText?.error = if (TextUtils.isEmpty(customerName)) {
                "Please enter customer name"
            } else {
                null
            }

            binding.customerPhoneNumberTextField.editText?.error = if (TextUtils.isEmpty(customerContact)) {
                "Please enter customer phone number"
            } else {
                null
            }

            binding.customerEmailIdTextField.editText?.error = if (!TextUtils.isEmpty(customerEmail) && !Patterns.EMAIL_ADDRESS.matcher(customerEmail).matches()) {
                "Please enter valid email id"
            } else {
                null
            }

            if (!TextUtils.isEmpty(customerName) && !TextUtils.isEmpty(customerContact)) {
                customerServiceDashboardViewModel.enquireCustomer(
                    CustomerDetails(
                        customerName = customerName,
                        customerPhoneNumber = customerContact,
                        customerEmailId = customerEmail,
                        customerStatus = CustomerServiceEnum.CUSTOMER_ENQUIRED.getUserRoleAsStringForServer()
                    )
                )
            }
        }

        customerServiceDashboardViewModel.validationMessageLiveData.observe(viewLifecycleOwner) {
            AlertDialog.showAlert(
                requireContext(),
                "Customer Service",
                it,
                positiveButtonText = "OK"
            )
        }

        customerServiceDashboardViewModel.customerDetailsData.observe(viewLifecycleOwner) {
            it.data?.let { customerDetails ->
                customerEnquiryCallback(customerDetails)
                customerServiceDashboardViewModel.customerDetailsData.removeObservers(viewLifecycleOwner)
                dismiss()
            }
        }

        customerServiceDashboardViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it) {
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
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}