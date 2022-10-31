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
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.viewmodel.CustomerServiceDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentCustomerOnboardingBinding
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.UserIdTypeEnum
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

        }
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