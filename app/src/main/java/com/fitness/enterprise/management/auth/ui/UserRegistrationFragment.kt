package com.fitness.enterprise.management.auth.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding

class UserRegistrationFragment : Fragment() {

    private val TAG = UserRegistrationFragment::class.java.name

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)

        val userRoles = listOf("Platform Admin", "Branch Admin", "Branch Manager")
        val userRolesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userRoles)
        (binding.userRoleTextField.editText as? AutoCompleteTextView)?.setAdapter(userRolesAdapter)
        (binding.userRoleTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "Selected User Role: ${userRoles.get(position)}")
        }

//        https://developer.android.com/codelabs/camerax-getting-started#0
//        https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
        val userIdType = listOf("AADHAAR", "PAN", "Driving License", "Voter Id", "Passport")
        val userIdTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userIdType)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setAdapter(userIdTypeAdapter)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "Selected User Id Type: ${userIdType.get(position)}")
        }

        binding.signUpButton.setOnClickListener {

        }
        binding.alreadyHaveAnAccountTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
        }

        binding.userBranchTextField.editText?.inputType = InputType.TYPE_NULL
        binding.userBranchTextField.setEndIconOnClickListener {
            Log.d(TAG, "userBranchTextField clicked")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}