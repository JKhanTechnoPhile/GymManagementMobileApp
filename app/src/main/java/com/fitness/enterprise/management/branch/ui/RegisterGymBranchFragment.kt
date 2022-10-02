package com.fitness.enterprise.management.branch.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.databinding.FragmentRegisterGymBranchBinding
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding

class RegisterGymBranchFragment : Fragment() {

    private var _binding: FragmentRegisterGymBranchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterGymBranchBinding.inflate(inflater, container, false)

        binding.branchLocationTextField.editText?.inputType = InputType.TYPE_NULL
        binding.branchLocationTextField.setEndIconOnClickListener {
            findNavController().navigate(R.id.action_registerGymBranchFragment_to_searchGymBranchMapsFragment)
        }

        return binding.root
    }
}