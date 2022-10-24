package com.fitness.enterprise.management.subscription.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.databinding.FragmentGymSubscriptionDashboardBinding
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.subscription.viewmodel.GymSubscriptionDashboardViewModel
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GymSubscriptionDashboardFragment : Fragment() {

    private var _binding: FragmentGymSubscriptionDashboardBinding? = null
    private val binding get() = _binding!!

    private val gymSubscriptionDashboardViewModel by viewModels<GymSubscriptionDashboardViewModel>()

    private lateinit var gymSubscriptionsAdapter: GymSubscriptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Gym Subscription"
        }
        _binding = FragmentGymSubscriptionDashboardBinding.inflate(inflater, container, false)
        gymSubscriptionsAdapter = GymSubscriptionsAdapter(::onGymBranchClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        gymSubscriptionDashboardViewModel.getGymSubscriptions()
        binding.gymSubscriptionsDashboard.layoutManager = LinearLayoutManager(requireContext())//StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gymSubscriptionsDashboard.adapter = gymSubscriptionsAdapter
        binding.createSubscription.setOnClickListener {
            findNavController().navigate(R.id.action_gymSubscriptionDashboardFragment_to_createGymSubscriptionFragment)
        }
    }

    private fun bindObservers() {
        gymSubscriptionDashboardViewModel.gymSubscriptionsData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    gymSubscriptionsAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "Gym Subscription",
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
        findNavController().navigate(R.id.action_gymSubscriptionDashboardFragment_to_editGymSubscriptionFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}