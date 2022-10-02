package com.fitness.enterprise.management.branch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.viewmodel.GymBranchDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentGymBranchDashboardBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GymBranchDashboardFragment : Fragment() {

    private var _binding: FragmentGymBranchDashboardBinding? = null
    private val binding get() = _binding!!

    private val gymBranchDashboardViewModel by viewModels<GymBranchDashboardViewModel>()

    private lateinit var gymBranchesAdapter: GymBranchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGymBranchDashboardBinding.inflate(inflater, container, false)
        gymBranchesAdapter = GymBranchesAdapter(::onGymBranchClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        gymBranchDashboardViewModel.getGymBranches()
        binding.gymBranchesDashboard.layoutManager = LinearLayoutManager(requireContext())//StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gymBranchesDashboard.adapter = gymBranchesAdapter
        binding.createBranch.setOnClickListener {
            findNavController().navigate(R.id.action_gymBranchDashboardFragment_to_registerGymBranchFragment)
        }
    }

    private fun bindObservers() {
        gymBranchDashboardViewModel.gymBranchesData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    gymBranchesAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    AlertDialog.showAlert(
                        requireContext(),
                        "Gym Branches",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun onGymBranchClicked(gymBranch: GymBranch) {
        val bundle = Bundle()
        bundle.putString("gymBranchDetails", Gson().toJson(gymBranch))
        findNavController().navigate(R.id.action_gymBranchDashboardFragment_to_editGymBranchFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}