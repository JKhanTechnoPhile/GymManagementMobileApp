package com.fitness.enterprise.management.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.databinding.FragmentUserLoginBinding
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding

class UserLoginFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {

        }

        binding.alreadyHaveAnAccountTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userRegistrationFragment)
        }

        binding.forgotPasswordTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userResetPasswordFragment)
        }

        return binding.root
    }
}