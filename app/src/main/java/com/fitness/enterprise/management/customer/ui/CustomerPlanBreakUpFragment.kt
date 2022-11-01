package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.utils.Constants
import com.google.gson.Gson

class CustomerPlanBreakUpFragment : Fragment() {

    private var customerDetails: CustomerDetails? = null

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
    ): View? {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Customer Subscription"
        }

        val jsonCustomerDetails = arguments?.getString("customerDetails")
        if (jsonCustomerDetails != null) {
            Log.d(Constants.TAG, "CustomerDetails from previous screen: $jsonCustomerDetails")
            customerDetails = Gson().fromJson(jsonCustomerDetails, CustomerDetails::class.java)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_plan_break_up, container, false)
    }
}