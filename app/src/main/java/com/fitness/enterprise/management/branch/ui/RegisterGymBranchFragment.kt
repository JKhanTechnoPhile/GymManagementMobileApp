package com.fitness.enterprise.management.branch.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.model.LocationModel
import com.fitness.enterprise.management.databinding.FragmentRegisterGymBranchBinding
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding
import com.google.gson.Gson

class RegisterGymBranchFragment : Fragment() {

    private var _binding: FragmentRegisterGymBranchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterGymBranchBinding.inflate(inflater, container, false)

        binding.branchLocationTextField.setEndIconOnClickListener {
            setFragmentResultListener("ADD_LOCATION") { key, bundle ->
                clearFragmentResultListener(requestKey = "ADD_LOCATION")
                val lastSelectedLocation = Gson().fromJson(bundle.getString("SELECTED_LOCATION_MODEL"), LocationModel::class.java)
                Log.d("Fetch Location", "Address: ${lastSelectedLocation?.locationAddress} Lat: ${lastSelectedLocation?.locationLatitude} Long: ${lastSelectedLocation?.locationLongitude}")
                binding.branchLocationTextField.editText?.setText("Latitude: ${lastSelectedLocation?.locationLatitude} \nLongitude: ${lastSelectedLocation?.locationLongitude}")
                binding.branchFullAddressTextField.editText?.setText(lastSelectedLocation?.locationAddress)
            }
            findNavController().navigate(R.id.action_registerGymBranchFragment_to_searchGymBranchMapsFragment)
        }

        return binding.root
    }


}