package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.viewmodel.CustomerServiceDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentCustomerOnboardingBinding
import com.fitness.enterprise.management.utils.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerOnboardingFragment : Fragment() {

    private val TAG = CustomerOnboardingFragment::class.java.name

    private var _binding: FragmentCustomerOnboardingBinding? = null
    private val binding get() = _binding!!

    private val customerServiceDashboardViewModel by viewModels<CustomerServiceDashboardViewModel>()

    private var customerDetails: CustomerDetails? = null

    private var selectedCustomerIdType: UserIdTypeEnum? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Customer Registration"
        }

        val jsonCustomerDetails = arguments?.getString("customerDetails")
        if (jsonCustomerDetails != null) {
            Log.d(Constants.TAG, "CustomerDetails from previous screen: $jsonCustomerDetails")
            customerDetails = Gson().fromJson(jsonCustomerDetails, CustomerDetails::class.java)
        }
        _binding = FragmentCustomerOnboardingBinding.inflate(inflater, container, false)

        val userIdTypeAsList = UserIdTypeEnum.values()
        val userIdType = userIdTypeAsList.map { userIdTypeEnum -> userIdTypeEnum.getUserIdAsString() }
        val userIdTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userIdType)
        (binding.customerIdTypeTextField.editText as? AutoCompleteTextView)?.setAdapter(userIdTypeAdapter)
        (binding.customerIdTypeTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            selectedCustomerIdType = userIdTypeAsList.get(position)
            Log.d(TAG, "Selected User Id Type: ${selectedCustomerIdType?.getUserIdAsString()} and Code: ${selectedCustomerIdType?.getUserIdAsCode()}")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()

        binding.registerCustomerButton.setOnClickListener {
            val customerName = binding.customerNameTextField.editText?.text.toString()
            val customerPhoneNumber = binding.customerPhoneNumberTextField.editText?.text.toString()
            val customerEmailId = binding.customerEmailIdTextField.editText?.text.toString()
            val customerIdAsString = selectedCustomerIdType?.getUserIdAsString()
            val customerIdNumber = binding.customerIdNumberTextField.editText?.text.toString()
            val oneTimeRegistrationFee = binding.registrationFeeTextField.editText?.text.toString()

            val customerDetailsDuringReg = customerDetails?.copy(
                customerName = customerName,
                customerPhoneNumber = customerPhoneNumber,
                customerEmailId = customerEmailId,
                customerStatus = CustomerServiceEnum.CUSTOMER_REGISTERED.getUserRoleAsStringForServer(),
                customerIdType = customerIdAsString,
                customerIdProof = customerIdNumber
            )

            customerDetailsDuringReg?.let {
                customerServiceDashboardViewModel.registerCustomer(it)
            }
        }

        customerServiceDashboardViewModel.validationMessageLiveData.observe(viewLifecycleOwner) {
            AlertDialog.showAlert(
                requireContext(),
                "Customer Registration",
                it,
                positiveButtonText = "OK"
            )
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

        customerServiceDashboardViewModel.customerDetailsData.observe(viewLifecycleOwner) {
            it.data?.let { customerDetails ->
                navigateToCustomerPlanBreakupFragment(customerDetails)
                customerServiceDashboardViewModel.customerDetailsData.removeObservers(viewLifecycleOwner)
            }
        }
    }

    private fun navigateToCustomerPlanBreakupFragment(customerDetails: CustomerDetails) {
        Log.d(Constants.TAG, "CustomerDetails: $customerDetails")
        val bundle = Bundle()
        bundle.putString("customerDetails", Gson().toJson(customerDetails))
        findNavController().navigate(R.id.action_customerOnboardingFragment_to_customerPlanBreakUpFragment, bundle)
    }

    private fun setInitialData() {
        customerDetails?.let {
            binding.customerNameTextField.editText?.setText(it.customerName)

            binding.customerPhoneNumberTextField.editText?.setText(it.customerPhoneNumber)

            binding.customerEmailIdTextField.editText?.setText(it.customerEmailId)

            binding.customerIdTypeTextField.editText?.setText(it.customerIdType)

            binding.customerIdNumberTextField.editText?.setText(it.customerIdProof)

            binding.registrationFeeTextField.editText?.setText("100")
        }
    }
}