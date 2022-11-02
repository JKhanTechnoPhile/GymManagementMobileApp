package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.viewmodel.CustomerServiceDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentCustomerPlanBreakUpBinding
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerPlanBreakUpFragment : Fragment() {

    private val TAG = CustomerPlanBreakUpFragment::class.java.name

    private var _binding: FragmentCustomerPlanBreakUpBinding? = null
    private val binding get() = _binding!!

    private val customerServiceDashboardViewModel by viewModels<CustomerServiceDashboardViewModel>()

    private var customerDetails: CustomerDetails? = null

    private var selectedSubscription: GymSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.customerServiceDashboardFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Customer Subscription"
        }

        val jsonCustomerDetails = arguments?.getString("customerDetails")
        if (jsonCustomerDetails != null) {
            Log.d(Constants.TAG, "CustomerDetails from previous screen: $jsonCustomerDetails")
            customerDetails = Gson().fromJson(jsonCustomerDetails, CustomerDetails::class.java)
        }

        _binding = FragmentCustomerPlanBreakUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()
    }

    private fun setInitialData() {
        customerDetails?.let { customerDetails ->
            binding.customerName.text = HtmlCompat.fromHtml("<b>Customer Name:</b> ${customerDetails.customerName}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            customerDetails.customerName
            binding.customerContact.text = HtmlCompat.fromHtml("<b>Customer Contact:</b> ${customerDetails.customerPhoneNumber}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            customerDetails.customerPhoneNumber
            binding.customerStatus.text = HtmlCompat.fromHtml("<b>Customer Status:</b> ${Constants.getCustomerStatus(customerDetails.customerStatus)}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.customerDetailsViewMoreButton.setOnClickListener {
                navigateToCustomerOnBoardingFragment(customerDetails)
            }

            if (customerDetails.gymSubscriptionPlanCode == null) {
                binding.subscriptionDetailsLl.visibility = View.GONE
                binding.addSubscriptionDetailsLl.visibility = View.VISIBLE
                binding.noSubscriptionDetailsAddButton.setOnClickListener {
                    navigateChooseSubscriptionPlan()
                }
                binding.cardSubscriptionPaymentDetails.visibility = View.GONE
            } else {
                reloadSubscription()
            }
        }
        binding.subscribeOrRenewButton.setOnClickListener {

        }
    }

    private fun reloadSubscription() {
        binding.subscriptionDetailsLl.visibility = View.VISIBLE
        binding.addSubscriptionDetailsLl.visibility = View.GONE
        binding.subscriptionDetailsViewMoreButton.setOnClickListener {
            navigateChooseSubscriptionPlan()
        }
        binding.cardSubscriptionPaymentDetails.visibility = View.VISIBLE
    }

    private fun navigateChooseSubscriptionPlan() {
        setFragmentResultListener("SELECTED_SUBSCRIPTION") { key, bundle ->
            clearFragmentResultListener(requestKey = "SELECTED_SUBSCRIPTION")
            selectedSubscription = Gson().fromJson(bundle.getString("gymSubscriptionDetails"), GymSubscription::class.java)
            selectedSubscription?.let { gymSubscription ->
                reloadSubscription()
                binding.subscriptionName.text = HtmlCompat.fromHtml("<b>Subscription Name:</b> ${gymSubscription.gymSubscriptionName}", HtmlCompat.FROM_HTML_MODE_LEGACY)
                binding.subscriptionBasePrice.text = HtmlCompat.fromHtml("<b>Base Price: ${Constants.INR_CURRENCY}</b> ${Constants.currencyFormat(gymSubscription.gymSubscriptionBaseFare)}", HtmlCompat.FROM_HTML_MODE_LEGACY)

                binding.subscriptionFeeTextField.editText?.setText(gymSubscription.gymSubscriptionBaseFare)
            }
        }
        findNavController().navigate(R.id.action_customerPlanBreakUpFragment_to_chooseSubscriptionPlansFragment)
    }

    private fun navigateToCustomerOnBoardingFragment(customerDetails: CustomerDetails) {
        Log.d(Constants.TAG, "CustomerDetails: $customerDetails")
        val bundle = Bundle()
        bundle.putString("customerDetails", Gson().toJson(customerDetails))
        bundle.putBoolean("viewOnly", true)
        findNavController().navigate(R.id.action_customerPlanBreakUpFragment_to_customerOnboardingFragment, bundle)
    }
}