package com.fitness.enterprise.management.branch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.databinding.FragmentEditGymBranchBinding
import com.google.gson.Gson

class EditGymBranchFragment : Fragment() {

    private var _binding: FragmentEditGymBranchBinding? = null
    private val binding get() = _binding!!
    private lateinit var gymBranchDetails: GymBranch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditGymBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
    }

    private fun setInitialData() {
        val jsonGymBranchDetails = arguments?.getString("gymBranchDetails")
        if (jsonGymBranchDetails != null) {
            gymBranchDetails = Gson().fromJson(jsonGymBranchDetails, GymBranch::class.java)
            binding.branchNameTextField.editText?.setText(gymBranchDetails.gymName)
            binding.branchContactTextField.editText?.setText(gymBranchDetails.gymContact)
            binding.branchFullAddressTextField.editText?.setText(gymBranchDetails.gymFullAddress)
            binding.branchLocationTextField.editText?.setText("Latitude: ${gymBranchDetails.gymLocationLat} \nLongitude: ${gymBranchDetails.gymLocationLong}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}