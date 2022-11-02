package com.fitness.enterprise.management.customer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.databinding.FragmentChooseSubscriptionPlansBinding
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.subscription.ui.GymSubscriptionsAdapter
import com.fitness.enterprise.management.subscription.viewmodel.GymSubscriptionDashboardViewModel
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseSubscriptionPlansFragment : Fragment() {

    private var _binding: FragmentChooseSubscriptionPlansBinding? = null
    private val binding get() = _binding!!

    private val gymSubscriptionDashboardViewModel by viewModels<GymSubscriptionDashboardViewModel>()

    private lateinit var gymSubscriptionsAdapter: GymSubscriptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Subscriptions"
        }
        _binding = FragmentChooseSubscriptionPlansBinding.inflate(inflater, container, false)
        gymSubscriptionsAdapter = GymSubscriptionsAdapter(::onGymBranchClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        gymSubscriptionDashboardViewModel.getGymSubscriptionsByCode()
        binding.gymSubscriptionsDashboard.layoutManager = LinearLayoutManager(requireContext())//StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gymSubscriptionsDashboard.adapter = gymSubscriptionsAdapter
    }

    private fun bindObservers() {
        gymSubscriptionDashboardViewModel.gymSubscriptionsData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    if (it.data.isNullOrEmpty()) {
                        binding.gymSubscriptionsDashboard.visibility = View.GONE
                        binding.noSubscriptionsMessage.visibility = View.VISIBLE
                    } else {
                        binding.gymSubscriptionsDashboard.visibility = View.VISIBLE
                        binding.noSubscriptionsMessage.visibility = View.GONE
                        gymSubscriptionsAdapter.submitList(it.data)
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "Subscriptions",
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

    private fun onGymBranchClicked(gymSubscription: GymSubscription) {
        val bundle = Bundle()
        bundle.putString("gymSubscriptionDetails", Gson().toJson(gymSubscription))
        setFragmentResult(
            "SELECTED_SUBSCRIPTION",
            bundle
        )
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}