package com.fitness.enterprise.management.branch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.model.GymBranchCreateRequest
import com.fitness.enterprise.management.branch.model.LocationModel
import com.fitness.enterprise.management.branch.viewmodel.GymBranchDashboardViewModel
import com.fitness.enterprise.management.databinding.FragmentRegisterGymBranchBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterGymBranchFragment : Fragment() {

    private var _binding: FragmentRegisterGymBranchBinding? = null
    private val binding get() = _binding!!

    private val gymBranchDashboardViewModel by viewModels<GymBranchDashboardViewModel>()

    private lateinit var lastSelectedLocation: LocationModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Register Gym Branch"
        }
        _binding = FragmentRegisterGymBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.branchLocationTextField.setEndIconOnClickListener {
            setFragmentResultListener("ADD_LOCATION") { key, bundle ->
                clearFragmentResultListener(requestKey = "ADD_LOCATION")
                lastSelectedLocation = Gson().fromJson(bundle.getString("SELECTED_LOCATION_MODEL"), LocationModel::class.java)
                Log.d("Fetch Location", "Address: ${lastSelectedLocation.locationAddress} Lat: ${lastSelectedLocation.locationLatitude} Long: ${lastSelectedLocation.locationLongitude}")
                binding.branchLocationTextField.editText?.setText("Latitude: ${lastSelectedLocation.locationLatitude} \nLongitude: ${lastSelectedLocation.locationLongitude}")
                binding.branchFullAddressTextField.editText?.setText(lastSelectedLocation.locationAddress)
            }
            findNavController().navigate(R.id.action_registerGymBranchFragment_to_searchGymBranchMapsFragment)
        }

        binding.registerBranchButton.setOnClickListener {
            val gymBranchName = binding.branchNameTextField.editText?.text.toString()
            val gymContact = binding.branchContactTextField.editText?.text.toString()
            val gymFullAddress = binding.branchFullAddressTextField.editText?.text.toString()
            val gymLocationLatitude = lastSelectedLocation.locationLatitude
            val gymLocationLongitude = lastSelectedLocation.locationLongitude

            val createGymBranchCreateRequest = GymBranchCreateRequest(gymContact, gymFullAddress, gymLocationLatitude, gymLocationLongitude, gymBranchName)

            gymBranchDashboardViewModel.createGymBranch(createGymBranchCreateRequest)
        }

        gymBranchDashboardViewModel.validationMessageLiveData.observe(viewLifecycleOwner) {
            AlertDialog.showAlert(
                requireContext(),
                "Branch Registration",
                it,
                positiveButtonText = "OK"
            )
        }

        gymBranchDashboardViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "Branch Registration",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}